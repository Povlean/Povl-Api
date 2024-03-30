package com.ean.project.model.dto.interfaceinfo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 *
 * @TableName product
 */
@Data
public class InterfaceInvokeRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 转发后缀
     */
    private String requestUrl;

    /**
     * 请求参数
     */
    private String requestParams;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}