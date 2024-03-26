package com.ean.commonapi.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author:ean
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostVO {

    private Long postId;

    private Long userId;

    private String content;

    private String image;

    private String title;

    private Integer thumbNum;

    private Integer favourNum;

    private Integer commentNum;

    private String userName;

    private List<CommentVO> commentVOList;

    private String userAvatar;

    private String createTime;

}
