package com.ean.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ean.commonapi.model.bo.AnalysisInfoBO;
import com.ean.commonapi.model.entity.UserInterfaceInfo;

import java.util.List;

/**
 * @author:ean
 */
public interface AnalysisService extends IService<UserInterfaceInfo> {

    List<AnalysisInfoBO> getTopInvokeInterface();

}
