package com.ean.project.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ean.commonapi.model.bo.AnalysisInfoBO;
import com.ean.commonapi.model.entity.GlobalConfig;
import com.ean.commonapi.model.entity.UserInterfaceInfo;
import com.ean.commonapi.model.vo.UsingInterfaceCountVO;
import com.ean.project.common.ErrorCode;
import com.ean.project.exception.BusinessException;
import com.ean.project.mapper.GlobalConfigMapper;
import com.ean.project.mapper.UserInterfaceInfoMapper;
import com.ean.project.mapper.UserLogMapper;
import com.ean.project.service.AnalysisService;
import com.ean.project.service.InterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@Slf4j
public class AnalysisServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo> implements AnalysisService {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private GlobalConfigMapper globalConfigMapper;

    @Resource
    private UserLogMapper userLogMapper;

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
        List<AnalysisInfoBO> analysisInfoBOList = analysisList.stream().map(a -> {
            String resName = resMap.get(a.getInterfaceInfoId());
            return AnalysisInfoBO.builder()
                    .interfaceName(resName)
                    .count(a.getCount())
                    .build();
        }).collect(Collectors.toList());
        log.info("analysisInfoBOList===>" + analysisInfoBOList);
        return analysisInfoBOList;
    }

    @Override
    public List<UsingInterfaceCountVO> getDailyInterface() {
        List<UsingInterfaceCountVO> dailyInterfaceList = userInterfaceInfoMapper.getDailyInterface();
        if (CollectionUtil.isEmpty(dailyInterfaceList)) {
            return new ArrayList<>();
        }
        return dailyInterfaceList;
    }

    @Override
    public List<UsingInterfaceCountVO> getDailyLoginNum() {
        List<UsingInterfaceCountVO> usingInterfaceCountVOList = userLogMapper.getDailyLoginNum();
        if (CollectionUtil.isEmpty(usingInterfaceCountVOList)) {
            return new ArrayList<>();
        }
        return usingInterfaceCountVOList;
    }

    @Override
    public Long getOperationTime() {
        QueryWrapper<GlobalConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("configKey", "opTime");
        GlobalConfig globalConfig = globalConfigMapper.selectOne(wrapper);
        if (StringUtils.isAnyBlank(globalConfig.getConfigValue())) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "全局配置值无效");
        }
        String configValue = globalConfig.getConfigValue();
        return Long.parseLong(configValue);
    }
}
