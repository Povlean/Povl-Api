package com.ean.project.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户登录请求体
 *
 * @author yupi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    @NotNull(message = "账户不能为空")
    private String userAccount;

    @NotNull(message = "用户密码不能为空")
    private String userPassword;
}
