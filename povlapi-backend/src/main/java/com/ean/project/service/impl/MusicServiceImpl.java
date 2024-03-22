package com.ean.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ean.commonapi.model.entity.Music;
import com.ean.project.mapper.MusicMapper;
import com.ean.project.service.MusicService;
import org.springframework.stereotype.Service;

/**
* @author Asphyxia
* @description 针对表【music】的数据库操作Service实现
* @createDate 2024-03-22 15:25:11
*/
@Service
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music>
    implements MusicService {

}




