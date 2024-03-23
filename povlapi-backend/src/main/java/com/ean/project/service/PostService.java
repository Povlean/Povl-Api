package com.ean.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ean.commonapi.model.entity.Post;
import com.ean.commonapi.model.vo.PostVO;
import com.ean.project.common.DeleteRequest;
import com.ean.project.model.dto.post.PostAddRequest;
import com.ean.project.model.dto.post.PostUpdateRequest;

import javax.servlet.http.HttpServletRequest;

/**
* @author Asphyxia
* @description 针对表【post(帖子)】的数据库操作Service
* @createDate 2024-03-23 20:20:30
*/
public interface PostService extends IService<Post> {

    /**
    * @description: 添加社区文章
    * @author Ean
    * @date 2024/3/23 20:23
    */
    Long addPostContent(PostAddRequest postAddRequest, HttpServletRequest request);

    /**
    * @description: 更新社区文章
    * @author Ean
    * @date 2024/3/23 20:52
    */
    Boolean updatePostContent(PostUpdateRequest postUpdateRequest, HttpServletRequest request);

    /**
    * @description: 删除社区文章
    * @author Ean
    * @date 2024/3/23 21:22
    */
    Boolean deletePostContent(DeleteRequest deleteRequest, HttpServletRequest request);

    /**
    * @description: 根据id获取社区文章内容
    * @author Ean
    * @date 2024/3/23 21:38
    */
    PostVO getPostContentById(Long postId);
}
