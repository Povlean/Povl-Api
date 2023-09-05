package com.example.povlapi_flaten.client;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.example.povlapi_flaten.model.User;
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

    public PovlApiClient() {}

    public PovlApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public HashMap getClientKey() {
        HashMap<String, String> keyMap = new HashMap<>();
        keyMap.put(ACCESS_KEY, accessKey);
        keyMap.put(SECRET_KEY, secretKey);
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
        String result = HttpRequest.post(url)
                .addHeaders(getClientKey())
                .body(json)
                .execute()
                .body();
        return result;
    }

}
