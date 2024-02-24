package com.ean.commonapi.service;

import com.ean.commonapi.model.entity.InterfaceInfo;
import com.ean.commonapi.model.entity.User;

/**
 * 用户接口信息服务
 *
 */
public interface InnerUserInterfaceInfoService {

    /**
     * @description: 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);
}
