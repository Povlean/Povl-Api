package com.example.povlapi_flaten.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.povlapi_flaten.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
