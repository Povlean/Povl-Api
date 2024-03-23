package com.ean.project.model.dto.post;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 更新请求
 *
 * @TableName product
 */
@Data
public class PostUpdateRequest implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private String image;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private static final long serialVersionUID = 1L;

}