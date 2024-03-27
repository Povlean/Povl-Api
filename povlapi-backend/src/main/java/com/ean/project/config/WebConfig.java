package com.ean.project.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author:ean
 */
@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    public static final String BASE_PATH = "E:\\runoob\\Git-Repository\\Povl-Api\\povlapi-backend\\imgs";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("--------------静态资源映射启动---------------");
        registry.addResourceHandler("/image/**").addResourceLocations("file:" + BASE_PATH + "/");
    }
}
