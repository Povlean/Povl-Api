package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.pojo.InterfaceInfo;
import generator.service.InterfaceInfoService;
import generator.mapper.InterfaceInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author Asphyxia
* @description 针对表【interface_info】的数据库操作Service实现
* @createDate 2023-08-28 20:43:31
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService{

}




