package com.ean.commonapi.service;


import com.ean.commonapi.model.entity.User;

/**
 * 用户服务
 *
 * @author ean
 */
public interface InnerUserService {
    /**
    * @description: 获取调用者信息
    * @author Ean  
    * @date 2024/2/24 13:55  
    * @param accessKey
    * @param secretKey
    */
    User getInvokeUser(String accessKey, String secretKey);
}
