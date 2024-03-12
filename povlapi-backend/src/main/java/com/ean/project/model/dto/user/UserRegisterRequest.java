package com.ean.project.model.dto.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author ean
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    @NotNull(message = "用户昵称不能为空")
    private String userAccount;

    @NotNull(message = "用户昵称不能为空")
    private String userName;

    @NotNull(message = "密码不能为空")
    private String userPassword;

    @NotNull(message = "确认密码不能为空")
    private String checkPassword;
}
