package com.example.povlapi_flaten.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ean.client_sdk.model.User;


public interface UserService extends IService<User> {

    User getUserByUserAccount(String userAccount);

}
