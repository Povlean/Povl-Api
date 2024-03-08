package com.ean.project.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ean.commonapi.model.bo.AnalysisInfoBO;
import com.ean.commonapi.model.entity.UserInterfaceInfo;
import com.ean.project.common.ErrorCode;
import com.ean.project.exception.BusinessException;
import com.ean.project.mapper.UserInterfaceInfoMapper;
import com.ean.project.service.AnalysisService;
import com.ean.project.service.InterfaceInfoService;
import com.ean.project.service.UserInterfaceInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author:ean
 */
@Service
public class AnalysisServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo> implements AnalysisService {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Override
    public List<AnalysisInfoBO> getTopInvokeInterface() {
        List<UserInterfaceInfo> analysisList = userInterfaceInfoMapper.getTopInvokeInterface();
        if (CollectionUtil.isEmpty(analysisList)) {
            return new ArrayList<>();
        }
        List<Long> interfaceInfoIds = new ArrayList<>();
        analysisList.forEach(a -> {
            Long interfaceInfoId = a.getInterfaceInfoId();
            interfaceInfoIds.add(interfaceInfoId);
        });
        Map<Long, String> resMap = interfaceInfoService.getNameByIdList(interfaceInfoIds);
        return analysisList.stream().map(a -> {
            String resName = resMap.get(a.getInterfaceInfoId());
            return AnalysisInfoBO.builder()
                    .interfaceName(resName)
                    .totalNum(a.getTotalNum())
                    .build();
        }).collect(Collectors.toList());
    }
}
