package com.ean.commonapi.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author:ean
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class WeatherVO {

    private String cityName;

    private List<ForecastVO> forecastVOList;

}
