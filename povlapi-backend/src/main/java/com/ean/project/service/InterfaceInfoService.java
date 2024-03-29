package com.ean.project.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ean.commonapi.model.entity.InterfaceInfo;
import com.ean.project.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.ean.project.model.dto.interfaceinfo.InterfaceInvokeRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface InterfaceInfoService extends IService<InterfaceInfo> {

    Map<Long, String> getNameByIdList(List<Long> ids);

    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean isAdd);

    InterfaceInfo getInterfaceInfo(String path, String method);

    String invokeInterfaceInfo(HttpServletRequest request, InterfaceInvokeRequest interfaceInvokeRequest);

    Page<InterfaceInfo> listInterfaceInfoByPage(InterfaceInfoQueryRequest interfaceInfoQueryRequest);

}
