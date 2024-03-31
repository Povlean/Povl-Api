package com.ean.project.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ean.commonapi.model.entity.Music;
import com.ean.commonapi.model.vo.*;
import com.ean.project.common.BaseResponse;
import com.ean.project.common.ErrorCode;
import com.ean.project.common.ResultUtils;
import com.ean.project.exception.BusinessException;
import com.ean.project.service.MusicService;
import io.swagger.annotations.ApiOperation;
import io.swagger.util.Json;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:ean
 */
@Slf4j
@RestController
@RequestMapping("/basic")
public class BasicController {

    public static final String WEATHER_KEY = "128edb8cb717492580145a919826303e";

    public static final String BOOK_KEY = "ae1718d4587744b0b79f940fbef69e77";

    public static final String CLIENT_ID = "XkQ6F5LDT63KszN4lCdpc0oY";

    public static final String CLIENT_SECRET = "efQjAnMVqBBOwMpNMqwMQpxaePc00np3";

    @Resource
    private MusicService musicService;

    @ApiOperation("城市天气接口")
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
                .forecastVOList(forecastVOList)
                .cityName(cityName)
                .build();
        return ResultUtils.success(weatherVO);
    }

    @ApiOperation("热榜音乐接口")
    @GetMapping("/music")
    public BaseResponse<List<MusicVO>> getMusicTop10() {
        List<Music> list = musicService.list();
        if (CollectionUtil.isEmpty(list)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<MusicVO> musicVOList = list.stream().map(l -> MusicVO.builder()
                .music(l.getMusic())
                .category(l.getCategory())
                .description(l.getDescription())
                .singer(l.getSinger())
                .build()).collect(Collectors.toList());
        return ResultUtils.success(musicVOList);
    }

    @ApiOperation("书籍查询接口")
    @GetMapping("/book/{isbnNum}")
    public BaseResponse<SearchBookVO> searchBookByIsbn(@PathVariable String isbnNum) {
        if (StringUtils.isAnyBlank(isbnNum)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "书籍的isbn不能为空");
        }
        String url = "http://data.isbn.work/openApi/getInfoByIsbn?isbn=" + isbnNum + "&appKey=" + BOOK_KEY;
        String body = HttpRequest.get(url)
                .execute()
                .body();
        JSONObject jsonObject = JSON.parseObject(body);
        JSONObject data = jsonObject.getJSONObject("data");
        SearchBookVO searchBookVO = SearchBookVO.builder()
                .isbn(data.getString("isbn"))
                .bookName(data.getString("bookName"))
                .pressPlace(data.getString("pressPlace"))
                .author(data.getString("author"))
                .press(data.getString("press"))
                .pressDate(data.getString("pressDate"))
                .build();
        return ResultUtils.success(searchBookVO);
    }

    @ApiOperation("随机一言接口")
    @GetMapping("/words")
    public BaseResponse<String> randomWords() {
        String url = "https://tenapi.cn/v2/yiyan";
        String body = HttpRequest.get(url)
                .execute()
                .body();
        return ResultUtils.success(body);
    }

    @ApiOperation("网易云音乐信息查询")
    @PostMapping("/music/{id}")
    public BaseResponse<MusicInfoVO> musicInfo(@PathVariable String id) {
        String url = "https://tenapi.cn/v2/songinfo?id=" + id;
        String body = HttpRequest.post(url)
                .execute()
                .body();
        JSONObject jsonObject = JSONObject.parseObject(body);
        String data = jsonObject.getString("data");
        JSONObject jsonObject1 = JSONObject.parseObject(data);
        MusicInfoVO musicInfoVO = MusicInfoVO.builder()
                .album(jsonObject1.getString("album"))
                .cover(jsonObject1.getString("cover"))
                .sings(jsonObject1.getString("sings"))
                .songs(jsonObject1.getString("songs"))
                .id(id).build();
        return ResultUtils.success(musicInfoVO);
    }

    @ApiOperation("热榜新闻查询")
    @GetMapping("/news")
    public BaseResponse<List<NewsVO>> hotNewList() {
        String url = "https://tenapi.cn/v2/zhihuhot";
        String body = HttpRequest.get(url)
                .execute()
                .body();
        JSONArray jsonArray = JSONArray.parseArray(JSONObject.parseObject(body)
                .getString("data"));
        List<NewsVO> list = new ArrayList<>();
        for (int i = 0;i < 10;i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            NewsVO newsVO = NewsVO.builder().name(jsonObject.getString("name"))
                    .hot(jsonObject.getString("hot"))
                    .url(jsonObject.getString("url"))
                    .build();
            list.add(newsVO);
        }
        return ResultUtils.success(list);
    }

    @ApiOperation("随机头像生成")
    @GetMapping("/head")
    public BaseResponse<String> getRandomHead() {
        String url = "https://v2.api-m.com/api/head";
        String body = HttpRequest.get(url)
                .execute()
                .body();
        String data = JSONObject.parseObject(body).getString("data");
        return ResultUtils.success(data);
    }

    @ApiOperation("随机昵称生成")
    @GetMapping("/nickname")
    public BaseResponse<String> getRandomNickname() {
        String url = "https://api.codemao.cn/api/user/random/nickname";
        String body = HttpRequest.get(url)
                .execute()
                .body();
        String data = JSONObject.parseObject(body).getString("data");
        String nickname = JSONObject.parseObject(data).getString("nickname");
        return ResultUtils.success(nickname);
    }

    @ApiOperation("股票趋势接口")
    @GetMapping("/stock/{symbol}")
    public BaseResponse<StockVO> stock(@PathVariable String symbol) {
        String url = "https://stock.xueqiu.com/v5/stock/realtime/quotec.json?symbol=" + symbol;
        String body = HttpRequest.get(url)
                .execute()
                .body();
        String data = JSONObject.parseObject(body).getString("data");
        JSONObject jsonObject = JSONArray.parseArray(data).getJSONObject(0);
        StockVO stockVO = StockVO.builder()
                .symbol(jsonObject.getString("symbol"))
                .current(jsonObject.getString("current"))
                .amount(jsonObject.getString("amount"))
                .avg_price(jsonObject.getString("avg_price"))
                .amplitude(jsonObject.getString("amplitude"))
                .chg(jsonObject.getString("chg"))
                .float_market_capital(jsonObject.getString("float_market_capital"))
                .market_capital(jsonObject.getString("market_capital"))
                .high(jsonObject.getString("high"))
                .low(jsonObject.getString("low"))
                .last_close(jsonObject.getString("last_close"))
                .volume(jsonObject.getString("volume"))
                .trade_volume(jsonObject.getString("trade_volume"))
                .turnover_rate(jsonObject.getString("turnover_rate"))
                .timestamp(jsonObject.getString("timestamp"))
                .build();
        return ResultUtils.success(stockVO);
    }

    @ApiOperation("股票趋势接口")
    @GetMapping("/ai")
    public BaseResponse<StockVO> talkToErnie() {
        return null;
    }
}
