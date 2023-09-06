package com.example.povlapi_flaten.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.example.povlapi_flaten.model.User;
import com.example.povlapi_flaten.utils.SignUtil;

import java.util.HashMap;
import java.util.Map;
import static com.example.povlapi_flaten.constants.ApiConstant.ACCESS_KEY;
import static com.example.povlapi_flaten.constants.ApiConstant.SECRET_KEY;

/**
 * @author:Povlean
 */
public class PovlApiClient {

    public static final String API_URL = "http://192.168.43.59:7530/name";

    private String accessKey;
    private String secretKey;

    public PovlApiClient() {

    }

    public PovlApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public HashMap getClientKey(String body) {
        HashMap<String, String> keyMap = new HashMap<>();
        keyMap.put(ACCESS_KEY, accessKey);
        // 不能直接传递密钥
        // keyMap.put(SECRET_KEY, secretKey);
        // 生成随机数以防重放
        keyMap.put("nonce", RandomUtil.randomNumbers(4));
        keyMap.put("body", body);
        keyMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        keyMap.put("sign", SignUtil.getSign(body, secretKey));
        return keyMap;
    }

    public String getNameByGet(String name) {
        String url = API_URL + "/get";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.get(url, paramMap);
        return result;
    }

    public String getNameByPost(String name) {
        String url = API_URL + "/post";
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.post(url, paramMap);
        return result;
    }

    public String getUsernameByPost(User user) {
        String json = JSONUtil.toJsonStr(user);
        String url = API_URL + "/user";
        String result = HttpRequest
                .post(url)
                .addHeaders(getClientKey(json))
                .body(json)
                .execute()
                .body();
        return result;
    }

}
