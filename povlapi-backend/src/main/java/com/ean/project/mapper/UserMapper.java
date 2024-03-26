package com.ean.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ean.commonapi.model.entity.User;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface UserMapper extends BaseMapper<User> {

    List<User> getUserByIds(List<Long> ids);

}




