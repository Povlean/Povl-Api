package com.example.task;

import com.ean.commonapi.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.annotation.Configuration;

/**
 * @author:ean
 */
@Configuration
public class InvokeCountTask {

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;




}
