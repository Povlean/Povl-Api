package com.ean.project.service.impl.inner;

import com.ean.commonapi.model.entity.InterfaceInfo;
import com.ean.commonapi.service.InnerInterfaceInfoService;
import com.ean.project.service.InterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author:ean
 */
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Override
    public InterfaceInfo getInterfaceInfo(String path, String method) {
        return interfaceInfoService.getInterfaceInfo(path, method);
    }
}
