package com.example.povlapi_flaten.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ean.client_sdk.model.User;
import com.example.povlapi_flaten.mapper.UserMapper;
import com.example.povlapi_flaten.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/*
 * @author:Povlean
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserByUserAccount(String userAccount) {
        if (userAccount == null) {
            throw new RuntimeException("账户为空");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserAccount, userAccount);
        User user = this.getOne(queryWrapper);
        if (ObjectUtil.isEmpty(user)) {
            throw new RuntimeException("用户不存在");
        }
        return user;
    }

}
