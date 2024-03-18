package com.ean.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ean.commonapi.model.bo.AnalysisInfoBO;
import com.ean.commonapi.model.bo.DailyInterfaceBO;
import com.ean.commonapi.model.entity.UserInterfaceInfo;
import com.ean.commonapi.model.vo.UsingInterfaceCountVO;

import java.util.List;

/**
 * @author:ean
 */
public interface AnalysisService extends IService<UserInterfaceInfo> {

    /**
    * @description: 获取调用次数排行前五的接口
    * @author Ean
    * @date 2024/3/14 10:55
    */
    List<AnalysisInfoBO> getTopInvokeInterface();

    /**
     * @description: 获取每日接口调用次数
     * @author Ean
     * @date 2024/3/14 10:55
     */
    List<UsingInterfaceCountVO> getDailyInterface();

    /**
    * @description: 获取每日用户登录总量
    * @author Ean  
    * @date 2024/3/15 16:10
    */
    List<UsingInterfaceCountVO> getDailyLoginNum();

    /**
    * @description: 获取平台运营时间
    * @author Ean  
    * @date 2024/3/18 8:47
    */
    Long getOperationTime();
    
}
