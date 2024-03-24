package com.ean.project.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ean.commonapi.model.entity.Post;
import com.ean.commonapi.model.entity.User;
import com.ean.commonapi.model.vo.PostVO;
import com.ean.project.common.DeleteRequest;
import com.ean.project.common.ErrorCode;
import com.ean.project.constant.CommonConstant;
import com.ean.project.exception.BusinessException;
import com.ean.project.mapper.PostMapper;
import com.ean.project.mapper.UserMapper;
import com.ean.project.model.dto.post.PostAddRequest;
import com.ean.project.model.dto.post.PostQueryRequest;
import com.ean.project.model.dto.post.PostUpdateRequest;
import com.ean.project.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                .title(post.getTitle())
                .image(post.getImage())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Page<PostVO> listPostByPage(PostQueryRequest postQueryRequest, HttpServletRequest request) {
        if (postQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Post postQuery = new Post();
        BeanUtils.copyProperties(postQueryRequest, postQuery);
        // 获取分页参数
        long current = postQueryRequest.getCurrent();
        long size = postQueryRequest.getPageSize();
        String sortField = postQueryRequest.getSortField();
        String sortOrder = postQueryRequest.getSortOrder();
        // content 需支持模糊搜索
        String content = postQuery.getContent();
        postQuery.setContent(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 分页查询操作
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>(postQuery);
        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<Post> postPage = this.page(new Page<>(current, size), queryWrapper);
        // 获取发布文章的用户
        List<Long> userIds = list().stream()
                .map(Post::getUserId)
                .distinct().collect(Collectors.toList());
        QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.in("id", userIds);
        List<User> users = userMapper.selectList(queryWrapper1);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));
        if (CollectionUtil.isEmpty(userMap)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 可优化，但是赶进度所以写得很乱
        List<PostVO> postVOList = postPage.getRecords().stream().map((post) -> {
            Long userId = post.getUserId();
            User user = userMap.get(userId);
            return PostVO.builder()
                    .userAvatar(user.getUserAvatar())
                    .favourNum(post.getFavourNum())
                    .postId(post.getId()).userId(userId)
                    .userName(user.getUserName())
                    .thumbNum(post.getThumbNum())
                    .content(post.getContent())
                    .title(post.getTitle())
                    .image(post.getImage())
                    .build();
        }).collect(Collectors.toList());
        Page<PostVO> postVOPage = new Page<>();
        postVOPage.setRecords(postVOList);
        postVOPage.setPages(postPage.getPages());
        postVOPage.setCurrent(postPage.getCurrent());
        postVOPage.setSize(postPage.getSize());
        postVOPage.setTotal(postPage.getTotal());
        return postVOPage;
    }

    @Override
    public List<PostVO> listPost(PostQueryRequest postQueryRequest) {
        List<Post> postList = this.list();
        List<Long> userIds = postList.stream()
                .map(Post::getUserId)
                .distinct().collect(Collectors.toList());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", userIds);
        List<User> users = userMapper.selectList(queryWrapper);
        Map<Long, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getId, user -> user));
        if (CollectionUtil.isEmpty(userMap)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 可优化，但是赶进度所以写得很乱
        List<PostVO> postVOList = postList.stream().map((post) -> {
            Long userId = post.getUserId();
            User user = userMap.get(userId);
            return PostVO.builder()
                    .userAvatar(user.getUserAvatar())
                    .favourNum(post.getFavourNum())
                    .postId(post.getId()).userId(userId)
                    .userName(user.getUserName())
                    .thumbNum(post.getThumbNum())
                    .content(post.getContent())
                    .title(post.getTitle())
                    .image(post.getImage())
                    .build();
        }).collect(Collectors.toList());
        return postVOList;
    }
}




