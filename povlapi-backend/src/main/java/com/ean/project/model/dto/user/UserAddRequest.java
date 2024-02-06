package com.ean.project.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户创建请求
 *
 * @author Ean
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddRequest implements Serializable {

    /**
     * 用户昵称
     */
    @NotNull(message = "用户名不能为空")
    private String userName;

    /**
     * 账号
     */
    @NotNull(message = "账户不能为空")
    private String userAccount;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 性别
     */
    @NotNull(message = "性别不能为空")
    private Integer gender;

    /**
     * 用户角色: user, admin
     */
    @NotNull(message = "用户身份不能为空")
    private String userRole;

    /**
     * 密码
     */
    @NotNull(message = "用户密码不能为空")
    private String userPassword;

    private static final long serialVersionUID = 1L;
}