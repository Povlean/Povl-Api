package com.ean.project.service.impl.inner;

import com.ean.commonapi.model.entity.InterfaceInfo;
import com.ean.commonapi.service.InnerInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author:ean
 */
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Override
    public InterfaceInfo getInterfaceInfo(String path, String method) {
        return null;
    }
}
