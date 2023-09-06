package com.ean.client_sdk;

import com.ean.client_sdk.client.PovlApiClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:客户端请求Starter类
 * @author:Povlean
 */
@Configuration
@ConfigurationProperties("povlapi.client")
public class PovlApiClientConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public PovlApiClient povlApiClient() {
        return new PovlApiClient(accessKey, secretKey);
    }

}
