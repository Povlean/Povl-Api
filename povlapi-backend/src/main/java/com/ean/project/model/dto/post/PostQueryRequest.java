package com.ean.project.model.dto.post;

import com.ean.project.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 查询请求
 *
 * @author ean
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostQueryRequest extends PageRequest implements Serializable {

    private String title;

    private String content;

    private Long userId;

    private static final long serialVersionUID = 1L;
}