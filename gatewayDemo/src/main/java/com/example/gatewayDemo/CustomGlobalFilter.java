package com.example.gatewayDemo;

import cn.hutool.core.util.ObjectUtil;
import com.ean.client_sdk.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
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
import java.util.ArrayList;
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
        // 请求的模拟接口是否存在？
        // TODO:从数据库中查询模拟接口是否存在，以及请求方式是否匹配
        // 调用失败，返回一个规范的异常
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("调用状态错误");
        }
        if (isSuccess) {
            return handleResponse(exchange, chain);
        }
        // TODO:统一错误码类型
        return null;
    }

    // 校验AK SK用户权限
    public boolean verifyRoot(HttpHeaders headers) {
        String accessKey = headers.getFirst(ACCESS_KEY);
        // 从实现类中查出数据库中的用户
        // User currentUser = userService.getUserByUserAccount("demo");
        // 从数据库中取出AK进行校验
        if (!accessKey.equals("123456")) {
            throw new RuntimeException("无权限");
        }
        String nonce = headers.getFirst(NONCE);
        if (Long.parseLong(nonce) > 10000) {
            throw new RuntimeException("无权限");
        }
        String body = headers.getFirst(BODY);
        String metaSign = SignUtil.getSign(body, "123456");
        String receiveSign = headers.getFirst(SIGN);
        if (!receiveSign.equals(metaSign)) {
            throw new RuntimeException("签名有误");
        }
        // 响应时间超过五分钟
        long currentTime = System.currentTimeMillis();
        String timestamp = headers.getFirst(TIMESTAMP);
        long time = Long.parseLong(timestamp) * 10000;
        if (currentTime - time > FIVE_MINUTE) {
            throw new RuntimeException("超过接口有效时长");
        }
        return true;
    }

    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain) {
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
