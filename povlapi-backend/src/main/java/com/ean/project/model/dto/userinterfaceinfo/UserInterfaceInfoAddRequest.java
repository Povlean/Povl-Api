package com.ean.project.model.dto.userinterfaceinfo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 创建请求
 *
 */
@Data
public class UserInterfaceInfoAddRequest implements Serializable {

    /**
     * 调用用户 id
     */
    @NotNull(message = "用户id不能为空")
    private Long userId;

    /**
     * 接口 id
     */
    @NotNull(message = "接口信息id不能为空")
    private Long interfaceInfoId;

    /**
     * 总调用次数
     */
    @NotNull(message = "总共调用次数不能为空")
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    @NotNull(message = "剩余调用次数不能为空")
    private Integer leftNum;

}