package com.example.task;

import com.ean.commonapi.model.bo.InvokeCountBO;
import com.ean.commonapi.model.constant.ApiConstant;
import com.ean.commonapi.service.InnerUserInterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author:ean
 */
@Slf4j
@Component
public class InvokeCountTask {

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Scheduled(cron = "*/2 * * * *")
    public void writeInvokeCount() {
        System.out.println("定时任务执行");
        Set<String> keys = redisTemplate.keys("invoke:count");
        for (String key : keys) {
            Object obj = redisTemplate.opsForValue().get(key);
            InvokeCountBO invokeCountBO = (InvokeCountBO) obj;
            log.info("invokeCountBO==>" + invokeCountBO);
        }
    }
}
