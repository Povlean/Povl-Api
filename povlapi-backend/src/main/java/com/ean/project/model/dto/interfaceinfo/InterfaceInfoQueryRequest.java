package com.ean.project.model.dto.interfaceinfo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ean.project.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 *
 * @author ean
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 接口名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 接口路径
     */
    private String url;

    /**
     * 请求类型
     */
    private String type;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /*
     *  接口状态
     * */
    private Integer status;

    /*
     * 请求方式
     * */
    private String method;

    /*
     * 用户ID
     * */
    private Long userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}