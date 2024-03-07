package com.ean.project.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ean.commonapi.model.entity.UserInterfaceInfo;
import com.ean.project.common.ErrorCode;
import com.ean.project.exception.BusinessException;
import com.ean.project.mapper.UserInterfaceInfoMapper;
import com.ean.project.service.AnalysisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:ean
 */
@Service
public class AnalysisServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo> implements AnalysisService {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Override
    public List<UserInterfaceInfo> getTopInvokeInterface(int limit) {
        if (limit <= 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<UserInterfaceInfo> analysisList = userInterfaceInfoMapper
                .getTopInvokeInterface(limit);
        if (CollectionUtil.isEmpty(analysisList)) {
            return new ArrayList<>();
        }
        return analysisList;
    }
}
