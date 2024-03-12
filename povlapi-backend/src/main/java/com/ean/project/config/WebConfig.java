package com.ean.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author:ean
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    public static final String BASE_PATH = "E:\\runoob\\Git-Repository\\Povl-Api\\povlapi-frontend\\src\\image";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations("file:" + BASE_PATH);
    }
}
