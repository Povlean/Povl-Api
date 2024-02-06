package com.example.povlapi_flaten;

import com.ean.client_sdk.client.PovlApiClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class PovlApiFlatenApplicationTests {

    @Resource
    private PovlApiClient povlApiClient;

}
