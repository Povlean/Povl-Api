package com.ean.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ean.project.model.entity.InterfaceInfo;

public interface InterfaceInfoService extends IService<InterfaceInfo> {

    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean isAdd);
}
