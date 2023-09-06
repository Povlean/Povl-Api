package com.example.povlapi_flaten.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ean.client_sdk.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
