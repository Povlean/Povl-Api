package com.ean.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ean.commonapi.model.entity.PostComment;
import com.ean.project.mapper.PostCommentMapper;
import com.ean.project.service.PostCommentService;
import org.springframework.stereotype.Service;

/**
* @author Asphyxia
* @description 针对表【post_comment】的数据库操作Service实现
* @createDate 2024-03-26 00:14:04
*/
@Service
public class PostCommentServiceImpl extends ServiceImpl<PostCommentMapper, PostComment>
    implements PostCommentService {

}




