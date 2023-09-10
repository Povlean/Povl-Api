package com.example.povlapi_flaten;

import com.ean.client_sdk.client.PovlApiClient;
import com.ean.client_sdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class PovlApiFlatenApplicationTests {

    @Resource
    private PovlApiClient povlApiClient;

    @Test
    public void sdkTest() {
        // 需要输入用户账户才能使用接口
        User user = new User();
        user.setUserAccount("povl");
        String result = povlApiClient.getUsernameByPost(user);
        System.out.println(result);
    }

    @Test
    public void sdkTest02() {
        String test = povlApiClient.getNameByGet("test");
        System.out.println(test);
    }

}
