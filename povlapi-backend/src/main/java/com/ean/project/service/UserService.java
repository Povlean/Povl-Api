package com.ean.project.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ean.commonapi.model.entity.User;
import com.ean.project.model.dto.user.UserAddRequest;
import com.ean.project.model.dto.user.UserLoginRequest;
import com.ean.project.model.dto.user.UserRegisterRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
 *
 * @author yupi
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userRegisterRequest 用户注册请求
     * @return 新用户 id
     */
    long userRegister(UserRegisterRequest userRegisterRequest);

    /**
     * 用户登录
     * @param userLoginRequest  用户账户
     * @return 脱敏后的用户信息
     */
    User userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request);

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);

    /**
    * @description: 添加用户
    * @author Ean
    */
    void addUser(UserAddRequest user);
}
