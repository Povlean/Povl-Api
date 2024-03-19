package com.ean.commonapi.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:ean
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ForecastVO {

    private String tempMax;

    private String tempMin;

    private String textDay;

    private String fxDate;

}
