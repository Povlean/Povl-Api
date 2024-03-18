package com.ean.project.controller;

import com.ean.commonapi.model.bo.AnalysisInfoBO;
import com.ean.commonapi.model.bo.DailyInterfaceBO;
import com.ean.commonapi.model.vo.DailyInterfaceVO;
import com.ean.commonapi.model.vo.InterfaceAnalVO;
import com.ean.commonapi.model.vo.UsingInterfaceCountVO;
import com.ean.project.common.BaseResponse;
import com.ean.project.common.ResultUtils;
import com.ean.project.convert.IInterfaceInfoMapper;
import com.ean.project.service.AnalysisService;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/analysis")
public class AnalysisController {

    @Resource
    private AnalysisService analysisService;

    @Resource
    private IInterfaceInfoMapper iInterfaceInfoMapper;

    @ApiOperation("统计总共接口调用次数")
    @GetMapping("/top")
    public BaseResponse<List<InterfaceAnalVO>> getTopInvokeInterface() {
        List<AnalysisInfoBO> topInvokeInterface = analysisService.getTopInvokeInterface();
        return ResultUtils.success(iInterfaceInfoMapper.interfaceToAnalVOList(topInvokeInterface));
    }

    @ApiOperation("统计每日接口调用次数")
    @GetMapping("/day/top")
    public BaseResponse<List<UsingInterfaceCountVO>> getDailyInterface() {
        List<UsingInterfaceCountVO> dailyInterfaceList = analysisService.getDailyInterface();
        return ResultUtils.success(dailyInterfaceList);
    }

    @ApiOperation("统计每日在线人数")
    @GetMapping("/login")
    public BaseResponse<List<UsingInterfaceCountVO>> getDailyLoginNum() {
        List<UsingInterfaceCountVO> dailyLoginList = analysisService.getDailyLoginNum();
        return ResultUtils.success(dailyLoginList);
    }

    @ApiOperation("显示网站运营时间")
    @GetMapping("/dev")
    public BaseResponse<Long> getOperationTime() {
        Long time = analysisService.getOperationTime();
        return ResultUtils.success(time);
    }
}
