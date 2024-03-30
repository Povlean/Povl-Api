package com.ean.client_sdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.ean.client_sdk.model.User;
import com.ean.client_sdk.utils.SignUtil;
import com.ean.client_sdk.model.Number;

import java.util.HashMap;
import java.util.Map;

import static com.ean.client_sdk.constants.ApiConstant.*;

/*
 * @description:API请求客户端
 * @author:Povlean
 */
public class PovlApiClient {

    public static final String GATEWAY_HOST = "http://127.0.0.1:8090/";

    private final String accessKey;

    private final String secretKey;

    private final String requestUrl;

    public PovlApiClient(String accessKey, String secretKey, String requestUrl) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.requestUrl = requestUrl;
    }

    public HashMap getClientKey(String body) {
        HashMap<String, String> keyMap = new HashMap<>();
        keyMap.put(ACCESS_KEY, accessKey);
        keyMap.put(SECRET_KEY, secretKey);
        // 生成随机数以防重放
        keyMap.put(NONCE, RandomUtil.randomNumbers(4));
        keyMap.put(BODY, body);
        keyMap.put(TIMESTAMP, String.valueOf(System.currentTimeMillis() / 1000));
        keyMap.put(SIGN, SignUtil.getSign(body, secretKey));
        return keyMap;
    }

    public String getNameByGet(String name) {
        String url = GATEWAY_HOST + "/api/name/get";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        return HttpUtil.get(url, paramMap);
    }

    public String getNameByPost(String name) {
        String url = GATEWAY_HOST + "/api/name/post";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.post(url, paramMap);
        return result;
    }

    public String getUsernameByPost(User user) {
        String json = JSONUtil.toJsonStr(user);
        // 这里的路由转发是写死的
        // 后面的路径需要从数据库中获取
        String url = GATEWAY_HOST + this.requestUrl;
        String result = HttpRequest
                .post(url)
                .addHeaders(getClientKey(json))
                .body(json)
                .execute()
                .body();
        return result;
    }

    public String algoAddNumber(Number numberRequest, User user) {
        String number = JSONUtil.toJsonStr(numberRequest);
        String json = JSONUtil.toJsonStr(user);
        // 这里的路由转发是写死的
        // 后面的路径需要从数据库中获取
        String url = GATEWAY_HOST + this.requestUrl;
        String result = HttpRequest
                .post(url)
                .addHeaders(getClientKey(json))
                .body(number)
                .execute()
                .body();
        return result;
    }

    public String minusNumber(Number numberRequest, User user) {
        String number = JSONUtil.toJsonStr(numberRequest);
        String json = JSONUtil.toJsonStr(user);
        // 这里的路由转发是写死的
        // 后面的路径需要从数据库中获取
        String url = GATEWAY_HOST + this.requestUrl;
        String result = HttpRequest
                .post(url)
                .addHeaders(getClientKey(json))
                .body(number)
                .execute()
                .body();
        return result;
    }

    public String mutilNumber(Number numberRequest, User user) {
        String number = JSONUtil.toJsonStr(numberRequest);
        String json = JSONUtil.toJsonStr(user);
        // 这里的路由转发是写死的
        // 后面的路径需要从数据库中获取
        String url = GATEWAY_HOST + this.requestUrl;
        String result = HttpRequest
                .post(url)
                .addHeaders(getClientKey(json))
                .body(number)
                .execute()
                .body();
        return result;
    }

    public String dividedNumber(Number numberRequest, User user) {
        String number = JSONUtil.toJsonStr(numberRequest);
        String json = JSONUtil.toJsonStr(user);
        // 这里的路由转发是写死的
        // 后面的路径需要从数据库中获取
        String url = GATEWAY_HOST + this.requestUrl;
        String result = HttpRequest
                .post(url)
                .addHeaders(getClientKey(json))
                .body(number)
                .execute()
                .body();
        return result;
    }

}
