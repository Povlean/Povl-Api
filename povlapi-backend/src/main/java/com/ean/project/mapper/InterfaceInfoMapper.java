package com.ean.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ean.commonapi.model.entity.InterfaceInfo;

import java.util.List;

public interface InterfaceInfoMapper extends BaseMapper<InterfaceInfo> {
    
    /**
    * @description: 根据id封装接口名字
    * @author Ean  
    * @date 2024/3/7 21:18
    */
    List<InterfaceInfo> getNameByIds(List<Long> ids);
}
