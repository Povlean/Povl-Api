package com.ean.project.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ean.commonapi.model.entity.User;
import com.ean.commonapi.model.entity.UserLog;
import com.ean.commonapi.model.vo.LogDataVO;
import com.ean.project.mapper.UserLogMapper;
import com.ean.project.mapper.UserMapper;
import com.ean.project.service.UserLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private UserMapper userMapper;

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

    @Override
    public List<LogDataVO> getLogDataList() {
        List<Long> userIds = userLogMapper.getUserIdInLog();
        if (CollectionUtil.isEmpty(userIds)) {
            return new ArrayList<>();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", userIds).orderBy(false, false, "createTime");
        List<User> users = userMapper.selectList(queryWrapper);
        if (CollectionUtil.isEmpty(users)) {
            return new ArrayList<>();
        }
        Map<Long, String> userMap = users.stream()
                .collect(Collectors.toMap(User::getId, User::getUserAccount));
        List<UserLog> userLogs = this.list();
        return userLogs.stream().map(u -> {
            String account = userMap.get(u.getUserId());
            return LogDataVO.builder().userAccount(account)
                    .createTime(u.getCreateTime().toString())
                    .operation(u.getOperation())
                    .build();
        }).collect(Collectors.toList());
    }

}




