package com.ean.project.controller;

import com.ean.commonapi.model.bo.AnalysisInfoBO;
import com.ean.commonapi.model.vo.InterfaceAnalVO;
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

    @ApiOperation("获取接口调用次数")
    @GetMapping("/top")
    public BaseResponse<List<InterfaceAnalVO>> getTopInvokeInterface() {
        List<AnalysisInfoBO> topInvokeInterface = analysisService.getTopInvokeInterface();
        return ResultUtils.success(iInterfaceInfoMapper.interfaceToAnalVOList(topInvokeInterface));
    }

}
