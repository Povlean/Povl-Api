package com.ean.commonapi.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author:ean
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisInfoBO {

    @NotNull(message = "接口名不能为空")
    private String interfaceName;

    @NotNull(message = "统计调用次数不能为空")
    private Integer count;

}
