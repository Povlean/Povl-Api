package com.ean.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ean.commonapi.model.entity.UserLog;
import com.ean.project.mapper.UserLogMapper;
import com.ean.project.service.UserLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author Asphyxia
* @description 针对表【user_log】的数据库操作Service实现
* @createDate 2024-03-15 15:32:06
*/
@Service
public class UserLogServiceImpl extends ServiceImpl<UserLogMapper, UserLog>
    implements UserLogService {

    public static final String OPS_LOGIN = "LOGIN";

    @Resource
    private UserLogMapper userLogMapper;

    @Override
    public Boolean addLogOfLogin(Long userId) {
        UserLog userLog = UserLog.builder()
                .userId(userId)
                .operation(OPS_LOGIN).build();
        int insert = userLogMapper.insert(userLog);
        return insert > 0;
    }
}




