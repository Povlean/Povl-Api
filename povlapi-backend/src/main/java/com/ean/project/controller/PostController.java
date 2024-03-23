package com.ean.project.controller;

import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ean.commonapi.model.entity.Post;
import com.ean.commonapi.model.vo.PostVO;
import com.ean.project.common.BaseResponse;
import com.ean.project.common.DeleteRequest;
import com.ean.project.common.ErrorCode;
import com.ean.project.common.ResultUtils;
import com.ean.project.constant.CommonConstant;
import com.ean.project.exception.BusinessException;
import com.ean.project.model.dto.post.PostAddRequest;
import com.ean.project.model.dto.post.PostQueryRequest;
import com.ean.project.model.dto.post.PostUpdateRequest;
import com.ean.project.service.PostService;
import com.ean.project.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 帖子接口
 *
 * @author ean
 */
@Slf4j
@RestController
@RequestMapping("/post")
public class PostController {

    @Resource
    private PostService postService;

    @Resource
    private UserService userService;

    @ApiOperation("添加社区文章")
    @PostMapping("/add")
    public BaseResponse<Long> addPost(@RequestBody @Validated PostAddRequest postAddRequest,
                                      HttpServletRequest request) {
        Long postId = postService.addPostContent(postAddRequest, request);
        return ResultUtils.success(postId);
    }

    @ApiOperation("更新文章内容")
    @PostMapping("/update")
    public BaseResponse<Boolean> updatePost(@RequestBody @Validated PostUpdateRequest postUpdateRequest,
                                            HttpServletRequest request) {
        Boolean isSuccess = postService.updatePostContent(postUpdateRequest, request);
        return ResultUtils.success(isSuccess);
    }


    @ApiOperation("删除社区文章")
    @PostMapping("/delete")
    public BaseResponse<Boolean> deletePost(@RequestBody @Validated DeleteRequest deleteRequest,
                                            HttpServletRequest request) {
        Boolean success = postService.deletePostContent(deleteRequest, request);
        return ResultUtils.success(success);
    }

    @ApiOperation("根据社区ID获取内容")
    @GetMapping("/get/{postId}")
    public BaseResponse<PostVO> getPostById(@PathVariable Long postId) {
        PostVO postVO = postService.getPostContentById(postId);
        return ResultUtils.success(postVO);
    }

    // @AuthCheck(mustRole = "admin")
    // @GetMapping("/list")
    // public BaseResponse<List<Post>> listPost(PostQueryRequest postQueryRequest) {
    //     Post postQuery = new Post();
    //     if (postQueryRequest != null) {
    //         BeanUtils.copyProperties(postQueryRequest, postQuery);
    //     }
    //     QueryWrapper<Post> queryWrapper = new QueryWrapper<>(postQuery);
    //     List<Post> postList = postService.list(queryWrapper);
    //     return ResultUtils.success(postList);
    // }

    @ApiOperation("分页查询文章")
    @GetMapping("/list/page")
    public BaseResponse<Page<PostVO>> listPostByPage(PostQueryRequest postQueryRequest,
                                                   HttpServletRequest request) {
        Page<PostVO> postPage = postService.listPostByPage(postQueryRequest, request);
        return ResultUtils.success(postPage);
    }

}
