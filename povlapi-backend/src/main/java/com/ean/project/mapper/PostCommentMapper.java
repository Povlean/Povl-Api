package com.ean.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ean.commonapi.model.entity.PostComment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author Asphyxia
* @description 针对表【post_comment】的数据库操作Mapper
* @createDate 2024-03-26 00:14:04
* @Entity generator.domain.PostComment
*/
public interface PostCommentMapper extends BaseMapper<PostComment> {
    @Select("SELECT * FROM post_comment WHERE postId = #{postId}")
    List<PostComment> selectCommentsByPostId(@Param("postId") Long postId);

    @Select("SELECT DISTINCT postId FROM post_comment")
    List<Long> selectAllPostIds();

    @Select("SELECT DISTINCT userId FROM post_comment")
    List<Long> selectAllUserIds();
}




