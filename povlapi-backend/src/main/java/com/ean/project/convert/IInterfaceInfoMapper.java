package com.ean.project.convert;

import com.ean.commonapi.model.bo.AnalysisInfoBO;
import com.ean.commonapi.model.bo.DailyInterfaceBO;
import com.ean.commonapi.model.entity.InterfaceInfo;
import com.ean.commonapi.model.entity.UserInterfaceInfo;
import com.ean.commonapi.model.vo.DailyInterfaceVO;
import com.ean.commonapi.model.vo.InterfaceAnalVO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IInterfaceInfoMapper {

    List<InterfaceAnalVO> interfaceToAnalVOList(List<AnalysisInfoBO> interfaceInfo);

    InterfaceAnalVO interfaceToAnalVO(UserInterfaceInfo interfaceInfo);

    List<DailyInterfaceVO> dailyInterfaceBOToVO(List<DailyInterfaceBO> dailyInterfaceBOList);
}
