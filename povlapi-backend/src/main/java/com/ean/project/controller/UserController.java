package com.ean.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.ean.commonapi.model.entity.User;
import com.ean.project.common.BaseResponse;
import com.ean.project.common.DeleteRequest;
import com.ean.project.common.ErrorCode;
import com.ean.project.common.ResultUtils;
import com.ean.project.convert.IUserMapper;
import com.ean.project.exception.BusinessException;
import com.ean.project.model.dto.user.*;
import com.ean.project.model.vo.UserVO;
import com.ean.project.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户接口
 *
 * @author ean
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final String FILE_PATH = "E:\\file.txt";

    @Resource
    private UserService userService;

    @Resource
    private IUserMapper iUserMapper;

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody @Validated UserRegisterRequest userRegisterRequest) {
        long result = userService.userRegister(userRegisterRequest);
        return ResultUtils.success(result);
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody @Validated UserLoginRequest userLoginRequest, HttpServletRequest request) {
        User user = userService.userLogin(userLoginRequest, request);
        return ResultUtils.success(user);
    }

    @ApiOperation("用户登出")
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(@NotNull HttpServletRequest request) {
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    @ApiOperation("获取当前用户")
    @GetMapping("/get/login")
    public BaseResponse<UserVO> getLoginUser(HttpServletRequest request) {
        User user = userService.getLoginUser(request);
        return ResultUtils.success(iUserMapper.userToUserVO(user));
        // return null;
    }

    @ApiOperation("添加用户")
    @PostMapping("/add")
    public BaseResponse<Long> addUser(@RequestBody @Validated UserAddRequest userAddRequest, HttpServletRequest request) {
        userService.addUser(userAddRequest);
        return ResultUtils.success();
    }

    @ApiOperation("删除用户")
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    @ApiOperation("更新用户")
    @PostMapping("/update")
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest request) {
        boolean result = userService.updateUser(userUpdateRequest, request);
        return ResultUtils.success(result);
    }

    @ApiOperation("上传头像")
    @PostMapping("/upload")
    public BaseResponse<String> upload(MultipartFile file){
        String fileName = userService.uploadAvatar(file);
        return ResultUtils.success(fileName);
    }

    @ApiOperation("根据id获取用户")
    @GetMapping("/get")
    public BaseResponse<UserVO> getUserById(HttpServletRequest request) {
        User user = userService.getUserById(request);
        return ResultUtils.success(iUserMapper.userToUserVO(user));
        // return null;
    }

    @ApiOperation("生成密钥")
    @GetMapping("/generate")
    public ResponseEntity<org.springframework.core.io.Resource> generateTextFile(HttpServletRequest request) {
        ResponseEntity<org.springframework.core.io.Resource> res = userService.generateTextFile(FILE_PATH, request);
        return res;
    }

    @GetMapping("/list")
    public BaseResponse<List<UserVO>> listUser(UserQueryRequest userQueryRequest, HttpServletRequest request) {
        User userQuery = new User();
        if (userQueryRequest != null) {
            BeanUtils.copyProperties(userQueryRequest, userQuery);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(userQuery);
        List<User> userList = userService.list(queryWrapper);
        List<UserVO> userVOList = userList.stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(userVOList);
    }

    /**
     * 分页获取用户列表
     *
     * @param userQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<UserVO>> listUserByPage(UserQueryRequest userQueryRequest, HttpServletRequest request) {
        long current = 1;
        long size = 10;
        User userQuery = new User();
        if (userQueryRequest != null) {
            BeanUtils.copyProperties(userQueryRequest, userQuery);
            current = userQueryRequest.getCurrent();
            size = userQueryRequest.getPageSize();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(userQuery);
        Page<User> userPage = userService.page(new Page<>(current, size), queryWrapper);
        Page<UserVO> userVOPage = new PageDTO<>(userPage.getCurrent(), userPage.getSize(), userPage.getTotal());
        List<UserVO> userVOList = userPage.getRecords().stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        }).collect(Collectors.toList());
        userVOPage.setRecords(userVOList);
        return ResultUtils.success(userVOPage);
    }

}
