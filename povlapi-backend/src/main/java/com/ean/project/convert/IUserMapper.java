package com.ean.project.convert;

import com.ean.project.model.entity.User;
import com.ean.project.model.vo.UserVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserMapper {

    UserVO userToUserVO(User user);

}
