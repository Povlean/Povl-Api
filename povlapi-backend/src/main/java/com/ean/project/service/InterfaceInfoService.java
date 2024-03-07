package com.ean.project.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ean.commonapi.model.entity.InterfaceInfo;
import com.ean.project.model.dto.interfaceinfo.InterfaceInvokeRequest;

import javax.servlet.http.HttpServletRequest;

public interface InterfaceInfoService extends IService<InterfaceInfo> {

    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean isAdd);

    InterfaceInfo getInterfaceInfo(String path, String method);

    String invokeInterfaceInfo(HttpServletRequest request, InterfaceInvokeRequest interfaceInvokeRequest);
}
