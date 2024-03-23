package com.ean.project.model.dto.post;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 创建请求
 *
 * @TableName product
 */
@Data
public class PostAddRequest implements Serializable {

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private byte[] image;

    private static final long serialVersionUID = 1L;

}