package com.ean.project.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ean.commonapi.model.entity.Post;
import com.ean.commonapi.model.entity.User;
import com.ean.commonapi.model.vo.PostVO;
import com.ean.project.common.DeleteRequest;
import com.ean.project.common.ErrorCode;
import com.ean.project.exception.BusinessException;
import com.ean.project.mapper.PostMapper;
import com.ean.project.mapper.UserMapper;
import com.ean.project.model.dto.post.PostAddRequest;
import com.ean.project.model.dto.post.PostUpdateRequest;
import com.ean.project.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.text.DateFormat;

import static com.ean.project.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author Asphyxia
* @description 针对表【post(帖子)】的数据库操作Service实现
* @createDate 2024-03-23 20:20:30
*/
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post>
    implements PostService {

    @Resource
    private PostMapper postMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public Long addPostContent(PostAddRequest postAddRequest, HttpServletRequest request) {
        Post post = new Post();
        BeanUtils.copyProperties(postAddRequest, post);
        User currentUser = (User)request.getSession()
                .getAttribute(USER_LOGIN_STATE);
        post.setUserId(currentUser.getId());
        int count = postMapper.insert(post);
        if (count <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return post.getId();
    }

    @Override
    public Boolean updatePostContent(PostUpdateRequest postUpdateRequest, HttpServletRequest request) {
        if (ObjectUtil.isNull(postUpdateRequest)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long postId = postUpdateRequest.getId();
        Post metaPost = postMapper.selectById(postId);
        User currentUser = (User)request.getSession().getAttribute(USER_LOGIN_STATE);
        if (!metaPost.getUserId().equals(currentUser.getId())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "非文章作者无法更新内容");
        }
        Post post = new Post();
        BeanUtils.copyProperties(postUpdateRequest, post);
        int count = postMapper.updateById(post);
        return count > 0;
    }

    @Override
    public Boolean deletePostContent(DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User currentUser = (User)request.getSession().getAttribute(USER_LOGIN_STATE);
        long postId = deleteRequest.getId();
        // 判断是否存在
        Post oldPost = postMapper.selectById(postId);
        if (ObjectUtil.isNull(oldPost)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldPost.getUserId().equals(currentUser.getId())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        return removeById(postId);
    }

    @Override
    public PostVO getPostContentById(Long postId) {
        if (postId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Post post = postMapper.selectById(postId);
        if (post == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "不存在该文章");
        }
        Long userId = post.getUserId();
        User user = userMapper.selectById(userId);
        if (ObjectUtil.isNull(user)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "不存在该用户");
        }
        return PostVO.builder()
                .userAvatar(user.getUserAvatar())
                .favourNum(post.getFavourNum())
                .postId(postId).userId(userId)
                .userName(user.getUserName())
                .thumbNum(post.getThumbNum())
                .content(post.getContent())
                .image(post.getImage())
                .build();
    }
}




