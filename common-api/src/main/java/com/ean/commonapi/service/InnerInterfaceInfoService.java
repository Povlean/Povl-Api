package com.ean.commonapi.service;


import com.ean.commonapi.model.entity.InterfaceInfo;

public interface InnerInterfaceInfoService {

    /**
    * @description: 查看数据库中是否有该接口存在
    * @author Ean
    * @param path
    * @param method
    * @return InterfaceInfo
    */
    InterfaceInfo getInterfaceInfo(String path, String method);
}
