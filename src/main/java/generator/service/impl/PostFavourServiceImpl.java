package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.pojo.PostFavour;
import generator.service.PostFavourService;
import generator.mapper.PostFavourMapper;
import org.springframework.stereotype.Service;

/**
* @author Asphyxia
* @description 针对表【post_favour(帖子收藏)】的数据库操作Service实现
* @createDate 2023-08-28 20:43:47
*/
@Service
public class PostFavourServiceImpl extends ServiceImpl<PostFavourMapper, PostFavour>
    implements PostFavourService{

}




