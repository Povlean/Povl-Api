package com.ean.project.controller;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ean.commonapi.model.vo.ForecastVO;
import com.ean.commonapi.model.vo.WeatherVO;
import com.ean.project.common.BaseResponse;
import com.ean.project.common.ErrorCode;
import com.ean.project.common.ResultUtils;
import com.ean.project.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:ean
 */
@Slf4j
@RestController
@RequestMapping("/basic")
public class BasicController {

    public static final String WEATHER_KEY = "128edb8cb717492580145a919826303e";

    @GetMapping("/weather/{cityName}")
    public BaseResponse<WeatherVO> weatherCondition(@PathVariable String cityName) {
        if (StringUtils.isAnyBlank(cityName)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "城市名不能为空");
        }
        String url = "https://geoapi.qweather.com/v2/city/lookup?location=" + cityName + "&key=" + WEATHER_KEY;
        String body = HttpRequest.get(url)
                .execute()
                .body();
        JSONObject jsonObject = JSON.parseObject(body);
        String id = JSONObject.parseObject(JSONObject.toJSONString(jsonObject
                        .getJSONArray("location").get(0)))
                        .getString("id");
        String url1 = "https://devapi.qweather.com/v7/weather/7d?location=" + id + "&key=" + WEATHER_KEY;
        String body1 = HttpRequest.get(url1)
                .execute()
                .body();
        List<ForecastVO> forecastVOList = new ArrayList<>();
        String daily = JSONObject.parseObject(body1)
                .getString("daily");
        JSONArray jsonArray = JSONArray.parseArray(daily);
        for (int i = 0;i < jsonArray.size();i++) {
            ForecastVO.ForecastVOBuilder forecastVOBuilder = ForecastVO.builder();
            JSONObject jsonDay = jsonArray.getJSONObject(i);
            String fxDate = jsonDay.getString("fxDate");
            String tempMax = jsonDay.getString("tempMax");
            String tempMin = jsonDay.getString("tempMin");
            String textDay = jsonDay.getString("textDay");
            ForecastVO forecast = forecastVOBuilder.fxDate(fxDate)
                    .tempMax(tempMax)
                    .tempMin(tempMin)
                    .textDay(textDay)
                    .build();
            forecastVOList.add(forecast);
        }
        WeatherVO weatherVO = WeatherVO.builder()
                .cityName(cityName)
                .forecastVOList(forecastVOList)
                .build();
        return ResultUtils.success(weatherVO);
    }
}
