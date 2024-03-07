package com.example.gatewayDemo;

import cn.hutool.core.util.ObjectUtil;
import com.ean.client_sdk.utils.SignUtil;
import com.ean.commonapi.model.entity.InterfaceInfo;
import com.ean.commonapi.model.entity.User;
import com.ean.commonapi.service.InnerInterfaceInfoService;
import com.ean.commonapi.service.InnerUserInterfaceInfoService;
import com.ean.commonapi.service.InnerUserService;
import com.ean.project.common.ErrorCode;
import com.ean.project.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.ean.client_sdk.constants.ApiConstant.*;

/**
 * @author:ean
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    public static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1", "0:0:0:0:0:0:0:1");

    public static final long FIVE_MINUTE = 5 * 60l;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    @DubboReference
    private InnerUserService innerUserService;

    public CustomGlobalFilter() {
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1.获取请求
        ServerHttpRequest request = exchange.getRequest();
        String sourceAddress = Objects.requireNonNull(request.getLocalAddress()).getHostString();
        // 2.黑白名单
        ServerHttpResponse response = exchange.getResponse();
        if (!IP_WHITE_LIST.contains(sourceAddress)) {
            // 设置状态直接拦截
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        // 3.AK SK 用户鉴权
        HttpHeaders headers = request.getHeaders();
        boolean isSuccess = verifyRoot(headers);
        // 调用失败，返回一个规范的异常
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("调用状态错误");
        }
        InterfaceInfo interfaceInfo = innerInterfaceInfoService
                .getInterfaceInfo(request.getPath().toString(), request.getMethod().toString());
        log.info("interfaceInfo==>", interfaceInfo);
        String accessKey = headers.getFirst(ACCESS_KEY);
        User invokeUser = innerUserService.getInvokeUser(accessKey);
        log.info("user:invokeUser==>", invokeUser);
        if (isSuccess) {
            // 打印统一日志
            return handleResponse(exchange, chain, interfaceInfo.getId(), invokeUser.getId());
        } else {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }

    // 校验AK SK用户权限
    public boolean verifyRoot(HttpHeaders headers) {
        String accessKey = headers.getFirst(ACCESS_KEY);
        // 从数据库中取出AK进行校验
        User invokeUser = innerUserService.getInvokeUser(accessKey);
        if (ObjectUtil.isNull(invokeUser)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "数据不存在");
        }
        String nonce = headers.getFirst(NONCE);
        if (Long.parseLong(nonce) > 10000) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "数据不存在");
        }
        // 响应时间超过五分钟
        long currentTime = System.currentTimeMillis();
        String timestamp = headers.getFirst(TIMESTAMP);
        long time = Long.parseLong(timestamp) * 10000;
        if (currentTime - time > FIVE_MINUTE) {
            throw new RuntimeException("超过接口有效时长");
        }
        String sign = headers.getFirst(SIGN);
        String body = headers.getFirst(BODY);
        // 校验secretKey
        String secretKey = invokeUser.getSecretKey();
        String metaSign = SignUtil.getSign(body, secretKey);
        if (!metaSign.equals(sign)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return true;
    }

    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, long interfaceInfoId, long userId) {
        try {
            // 从交换机中获取到原响应值
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓存数据的工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 获取状态码
            HttpStatus statusCode = originalResponse.getStatusCode();
            if(statusCode == HttpStatus.OK){
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 调用完接口后，往返回值里写数据
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        //log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 增强响应值
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                // 调用后调用总量+1，剩余调用次数-1
                                innerUserInterfaceInfoService.invokeCount(interfaceInfoId, userId);
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);//释放掉内存
                                // 构建日志
                                String data = new String(content, StandardCharsets.UTF_8);//data
                                ServerHttpRequest request = exchange.getRequest();
                                log.info("请求唯一标识: {}, 请求路径: {}, 请求方法: {}, 响应值: {}, 响应码: {}",
                                        request.getId(), request.getPath(), request.getMethod(), data, getStatusCode());
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange);//降级处理返回数据
        }catch (Exception e){
            log.error("gateway log exception.\n" + e);
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
