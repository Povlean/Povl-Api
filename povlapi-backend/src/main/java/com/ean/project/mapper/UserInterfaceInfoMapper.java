package com.ean.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ean.commonapi.model.bo.DailyInterfaceBO;
import com.ean.commonapi.model.entity.UserInterfaceInfo;
import com.ean.commonapi.model.vo.UsingInterfaceCountVO;

import java.util.List;

/**
 * 用户接口信息 Mapper
 *
 */
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    List<UserInterfaceInfo> getTopInvokeInterface();

    List<UsingInterfaceCountVO> getDailyInterface();

    List<DailyInterfaceBO> dailyInterface();
}




