package com.ean.project.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ean.client_sdk.client.PovlApiClient;
import com.ean.commonapi.model.entity.InterfaceInfo;
import com.ean.commonapi.model.entity.User;
import com.ean.project.common.ErrorCode;
import com.ean.project.exception.BusinessException;
import com.ean.project.mapper.InterfaceInfoMapper;
import com.ean.project.model.dto.interfaceinfo.InterfaceInvokeRequest;
import com.ean.project.model.enums.PostStatusEnum;
import com.ean.project.service.InterfaceInfoService;
import com.ean.project.service.UserService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */
@Slf4j
@Service
@DubboService
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo> implements InterfaceInfoService {

    @Resource
    private UserService userService;

    private static final List<String> methodList = Arrays.asList("GET", "POST", "PUT", "DELETE");

    private static final String prefix = "http://localhost:7530";

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public Map<Long, String> getNameByIdList(List<Long> ids) {
        // 只查前五个出来
        List<InterfaceInfo> interfaceInfos = interfaceInfoMapper.getNameByIds(ids);
        if (CollectionUtil.isEmpty(interfaceInfos)) {
            return new HashMap<>();
        }
        Map<Long, String> resMap = new HashMap<>();
        interfaceInfos.forEach(i -> {
            resMap.put(i.getId(), i.getName());
        });
        return resMap;
    }

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean isAdd) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String method = interfaceInfo.getMethod();
        String name = interfaceInfo.getName();
        String requestHeader = interfaceInfo.getRequestHeader();
        String responseHeader = interfaceInfo.getResponseHeader();
        String type = interfaceInfo.getType();
        String url = interfaceInfo.getUrl();
        // 校验是否为添加项
        if (isAdd) {
            if (StringUtils.isAnyBlank(method, name, requestHeader, responseHeader, type, url)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        // 非添加项
        if (StringUtils.isBlank(name) || name.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (StringUtils.isAnyBlank(method, requestHeader, responseHeader, type, url)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }

    @Override
    public InterfaceInfo getInterfaceInfo(String path, String method) {
        if (StringUtils.isAnyBlank(path, method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数不存在");
        }
        if (!methodList.contains(method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "method参数不合理");
        }
        // /api/name/user
        String url = prefix + subApiSuffix(path);
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", url);
        queryWrapper.eq("method", method);
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectOne(queryWrapper);
        if (ObjectUtil.isNull(interfaceInfo)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "请求数据不存在");
        }
        return interfaceInfo;
    }

    @Override
    public String invokeInterfaceInfo(HttpServletRequest request, InterfaceInvokeRequest interfaceInvokeRequest) {
        if (interfaceInvokeRequest.getRequestParams() == null || interfaceInvokeRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = interfaceInvokeRequest.getId();
        String requestParams = interfaceInvokeRequest.getRequestParams();
        log.info("requestParams==>" + requestParams);
        // 判断接口是否存在
        InterfaceInfo oldInterfaceinfo = this.getById(id);
        if (ObjectUtil.isEmpty(oldInterfaceinfo)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        if (oldInterfaceinfo.getStatus() == PostStatusEnum.OFFLINE.getValue()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }        
        User loginUser = userService.getLoginUser(request);
        // 这里需要在请求体中校验 ak sk
        User user = verifyKey(requestParams, loginUser);
        // Client转发到网关层
        // 网关层转发到对应的请求路径中
        PovlApiClient tempClient = new PovlApiClient(loginUser.getAccessKey(), loginUser.getSecretKey());
        return tempClient.getUsernameByPost(user);
    }

    private User verifyKey(String requestParams, User loginUser) {
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        // 从请求参数中拆解ak sk
        Gson gson = new Gson();
        User user = gson.fromJson(requestParams, User.class);
        String reqAk = user.getAccessKey();
        String reqSk = user.getSecretKey();
        if (StringUtils.isAnyBlank(reqAk, reqSk)) {
            throw new BusinessException(ErrorCode.NO_ACESSKEY_SERECTKEY_ERROR);
        }
        if (!accessKey.equals(reqAk) || !secretKey.equals(reqSk)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return user;
    }

    private String subApiSuffix(String metaPath) {
        // 去除/api
        return metaPath.substring(4);
    }
}
