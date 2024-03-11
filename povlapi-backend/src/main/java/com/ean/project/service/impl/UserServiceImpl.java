package com.ean.project.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ean.commonapi.model.entity.User;
import com.ean.project.common.ErrorCode;
import com.ean.project.exception.BusinessException;
import com.ean.project.mapper.UserMapper;
import com.ean.project.model.dto.user.UserAddRequest;
import com.ean.project.model.dto.user.UserLoginRequest;
import com.ean.project.model.dto.user.UserRegisterRequest;
import com.ean.project.model.dto.user.UserUpdateRequest;
import com.ean.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.ean.project.constant.UserConstant.ADMIN_ROLE;
import static com.ean.project.constant.UserConstant.USER_LOGIN_STATE;


/**
 * 用户服务实现类
 *
 * @author ean
 */
@DubboService
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "povl";

    public static final String BASE_PATH = "D:\\tempImg\\";

    @Override
    public long userRegister(UserRegisterRequest userRegisterRequest) {
        // 1. 校验
        if (ObjectUtil.isNull(userRegisterRequest)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求不能为空");
        }
        String userAccount = userRegisterRequest.getUserAccount();
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        // 防止并发导致重复注册
        synchronized (userAccount.intern()) {
            // 账户不能重复
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getUserAccount, userAccount);
            long count = count(wrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 2. 加密
            List<String> encryptInfo = this.encryptInfo(Arrays.asList(userPassword, userAccount));
            // 3. 插入数据
            User user = User.builder().userAccount(userAccount)
                    .userPassword(encryptInfo.get(0))
                    .accessKey(encryptInfo.get(1))
                    .secretKey(encryptInfo.get(2)).build();
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return user.getId();
        }
    }

    @Override
    public User userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request) {
        // 1. 校验
        if (ObjectUtil.isNull(userLoginRequest)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        // 3. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        return user;
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        long userId = currentUser.getId();
        currentUser = this.getById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    @Override
    public boolean isAdmin(HttpServletRequest request) {
        // 校验request参数是否为空
        if (ObjectUtil.isNull(request)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && ADMIN_ROLE.equals(user.getUserRole());
    }

    @Override
    public boolean userLogout(HttpServletRequest request) {
        if (ObjectUtil.isNull(request)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "request为空");
        }
        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
        }
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }

    @Override
    public void addUser(UserAddRequest userAddRequest) {
        if (userAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtils.copyProperties(userAddRequest, user);
        boolean isSuccess = this.save(user);
        if (!isSuccess) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    @Override
    public boolean updateUser(UserUpdateRequest userUpdateRequest, HttpServletRequest request) {
        User attribute = (User)request.getSession().getAttribute(USER_LOGIN_STATE);
        userUpdateRequest.setId(attribute.getId().toString());
        if (userUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest, user);
        user.setId(Long.parseLong(userUpdateRequest.getId()));
        return updateById(user);
    }

    @Override
    public String uploadAvatar(MultipartFile file) {
        log.info(file.toString());
        String originalName = file.getOriginalFilename();
        String suffix = null;
        if (originalName != null) {
            suffix = originalName.substring(originalName.lastIndexOf("."));
        }
        String fileName = UUID.randomUUID() + suffix;
        File dir = new File(BASE_PATH);
        if(!dir.exists()){
            dir.mkdirs();
        }
        try{
            file.transferTo(new File(BASE_PATH + fileName));
        }catch (IOException e){
            e.printStackTrace();
        }
        return fileName;
    }

    @Override
    public User getUserById(HttpServletRequest request) {
        User loginUser = (User)request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = getById(loginUser.getId());
        if (ObjectUtil.isNull(user)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return user;
    }

    private List<String> encryptInfo(List<String> metaUserInfo) {
        String userPassword = metaUserInfo.get(0);
        String userAccount = metaUserInfo.get(1);
        // 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        String accessKey = DigestUtils.md5DigestAsHex((SALT + userAccount + RandomUtil.randomString(5)).getBytes());
        String secretKey = DigestUtils.md5DigestAsHex((SALT + userAccount + RandomUtil.randomString(5)).getBytes());
        return Arrays.asList(encryptPassword, accessKey, secretKey);
    }
}




