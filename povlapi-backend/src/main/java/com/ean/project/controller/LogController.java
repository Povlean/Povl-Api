package com.ean.project.controller;

import com.ean.commonapi.model.vo.LogDataVO;
import com.ean.project.common.BaseResponse;
import com.ean.project.common.ResultUtils;
import com.ean.project.service.UserLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:ean
 */
@Slf4j
@RestController
@RequestMapping("/sys")
public class LogController {

    @Resource
    private UserLogService userLogService;

    @GetMapping
    public BaseResponse<List<LogDataVO>> getLogDataList() {
        List<LogDataVO> logDataVOList = userLogService.getLogDataList();
        return ResultUtils.success(logDataVOList);
    }

}
