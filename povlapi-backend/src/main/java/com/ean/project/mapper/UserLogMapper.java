package com.ean.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ean.commonapi.model.entity.UserLog;
import com.ean.commonapi.model.vo.UsingInterfaceCountVO;

import java.util.List;

/**
* @author Asphyxia
* @description 针对表【user_log】的数据库操作Mapper
* @createDate 2024-03-15 15:32:06
* @Entity generator.domain.UserLog
*/
public interface UserLogMapper extends BaseMapper<UserLog> {

    List<UsingInterfaceCountVO> getDailyLoginNum();

    List<Long> getUserIdInLog();
}




