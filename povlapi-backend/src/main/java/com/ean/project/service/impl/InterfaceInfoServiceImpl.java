package com.ean.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ean.commonapi.model.entity.InterfaceInfo;
import com.ean.project.common.ErrorCode;
import com.ean.project.exception.BusinessException;
import com.ean.project.mapper.InterfaceInfoMapper;
import com.ean.project.service.InterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 */
@Service
@DubboService
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo> implements InterfaceInfoService {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

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
}
