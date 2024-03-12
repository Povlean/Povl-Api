package com.ean.client_sdk.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@TableName("user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @TableId
    private Long id;

    @TableField
    private String userAccount;

    @TableField
    private String userPassword;

    @TableField
    private String unionId;

    @TableField
    private String userName;

    @TableField
    private String userAvatar;

    @TableField
    private String userRole;

    @TableField
    private Timestamp createTime;

    @TableField
    private Timestamp updateTime;

    @TableLogic
    private Integer isDelete;

    @TableField
    private String accessKey;

    @TableField
    private String secretKey;
}
