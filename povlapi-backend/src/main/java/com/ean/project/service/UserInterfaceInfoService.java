package com.ean.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ean.commonapi.model.entity.UserInterfaceInfo;
import com.ean.project.common.DeleteRequest;
import com.ean.project.model.dto.userinterfaceinfo.UserInterfaceInfoAddRequest;
import com.ean.project.model.dto.userinterfaceinfo.UserInterfaceInfoUpdateRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户接口信息服务
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    /**
    * @description: 接口信息调用
    * @author Ean
    * @date 2024/3/11 9:12
    */
    boolean invokeCount(long interfaceInfoId, long userId);

    /**
    * @description: 添加用户接口映射
    * @author Ean
    * @date 2024/3/11 9:12
    */
    long addUserInterfaceInfo(UserInterfaceInfoAddRequest userInterfaceInfoAddRequest, HttpServletRequest request);

    /**
    * @description: 删除用户接口映射
    * @author Ean
    * @date 2024/3/11 9:18
    */
    boolean deleteUserInterfaceInfo(DeleteRequest deleteRequest, HttpServletRequest request);

    /**
    * @description: 更新用户接口映射
    * @author Ean
    * @date 2024/3/11 9:35
    */
    boolean updateUserInterfaceInfo(UserInterfaceInfoUpdateRequest userInterfaceInfoUpdateRequest, HttpServletRequest request);
}
