package com.ean.project.model.dto.comment;

import lombok.Data;

import java.io.Serializable;

/**
 * @author:ean
 */
@Data
public class AddCommentRequest implements Serializable {

    private Long id;

    private String comment;

}
