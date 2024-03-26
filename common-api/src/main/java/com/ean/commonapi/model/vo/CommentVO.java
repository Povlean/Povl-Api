package com.ean.commonapi.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author:ean
 */
@Data
@Builder
public class CommentVO {

    private String userName;

    private String comment;

    private String userAvatar;

}
