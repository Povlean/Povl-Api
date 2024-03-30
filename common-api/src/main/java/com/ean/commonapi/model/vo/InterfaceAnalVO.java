package com.ean.commonapi.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author:ean
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterfaceAnalVO {

    @NotNull(message = "接口名不能为空")
    private String interfaceName;

    @NotNull(message = "调用总数不能为空")
    private Integer count;

}
