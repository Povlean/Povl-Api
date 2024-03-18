package com.ean.commonapi.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author:ean
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyInterfaceVO implements Serializable {

    @NotNull(message = "接口名不能为空")
    private String interfaceName;

    @NotNull(message = "接口调用次数")
    private Integer count;

    @NotNull(message = "调用日期")
    private String date;
}
