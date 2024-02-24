package com.ean.project.service;

import com.ean.commonapi.model.entity.User;
import com.ean.project.convert.IUserMapper;
import com.ean.project.model.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Resource
    private IUserMapper iUserMapper;

    @Test
    void testAddUser() {
        User user = new User();
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        boolean result = userService.updateById(user);
        Assertions.assertTrue(result);
    }

    @Test
    void testDeleteUser() {
        boolean result = userService.removeById(1L);
        Assertions.assertTrue(result);
    }

    @Test
    void testGetUser() {
        User user = userService.getById(1633350906680512514L);
        Assertions.assertNotNull(user);
    }

    @Test
    void userRegister() {
        String userAccount = "demo";
        String password = "1234567890";
        String checkPassword = "1234567890";
    }

    @Test
    void mapStruct() {
        User user = User.builder().userName("zhangsan").userRole("管理员").build();
        UserVO userVO = iUserMapper.userToUserVO(user);
        log.info("result===>" + userVO);
    }

}