package com.ean.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ean.commonapi.model.entity.UserLog;

/**
* @author Asphyxia
* @description 针对表【user_log】的数据库操作Service
* @createDate 2024-03-15 15:32:06
*/
public interface UserLogService extends IService<UserLog> {

    Boolean addLogOfLogin(Long id);

}
