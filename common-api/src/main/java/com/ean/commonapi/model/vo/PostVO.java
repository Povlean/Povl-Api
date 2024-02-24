package com.ean.commonapi.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.ean.commonapi.model.entity.Post;

/**
 * 帖子视图
 *
 * @author ean
 * @TableName product
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostVO extends Post {

    /**
     * 是否已点赞
     */
    private Boolean hasThumb;

    private static final long serialVersionUID = 1L;
}