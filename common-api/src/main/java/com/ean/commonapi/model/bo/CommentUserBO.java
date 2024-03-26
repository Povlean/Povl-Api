package com.ean.commonapi.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author:ean
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentUserBO implements Serializable {

    private String userName;

    private String userAvatar;

}
