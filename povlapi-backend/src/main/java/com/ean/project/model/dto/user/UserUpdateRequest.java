package com.ean.project.model.dto.user;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户更新请求
 *
 * @author ean
 */
@Data
public class UserUpdateRequest implements Serializable {

    /**
     * id
     */
    private String id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 手机号
     */
    private String unionId;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 性别
     */
    private String gender;

    /**
     * 工作
     */
    private String job;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}