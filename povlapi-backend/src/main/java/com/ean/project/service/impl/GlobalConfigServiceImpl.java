package com.ean.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ean.commonapi.model.entity.GlobalConfig;
import com.ean.project.mapper.GlobalConfigMapper;
import com.ean.project.service.GlobalConfigService;
import org.springframework.stereotype.Service;

/**
* @author Asphyxia
* @description 针对表【global_config】的数据库操作Service实现
* @createDate 2024-03-18 08:49:29
*/
@Service
public class GlobalConfigServiceImpl extends ServiceImpl<GlobalConfigMapper, GlobalConfig>
    implements GlobalConfigService {

}




