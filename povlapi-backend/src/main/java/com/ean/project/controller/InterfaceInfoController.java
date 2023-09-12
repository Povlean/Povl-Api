package com.ean.project.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ean.client_sdk.client.PovlApiClient;
import com.ean.project.annotation.AuthCheck;
import com.ean.project.common.BaseResponse;
import com.ean.project.common.DeleteRequest;
import com.ean.project.common.ErrorCode;
import com.ean.project.common.ResultUtils;
import com.ean.project.constant.CommonConstant;
import com.ean.project.exception.BusinessException;
import com.ean.project.model.dto.interfaceinfo.InterfaceInfoAddRequest;
import com.ean.project.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.ean.project.model.dto.interfaceinfo.InterfaceInfoUpdateRequest;
import com.ean.project.model.dto.post.PostIdRequest;
import com.ean.project.model.entity.InterfaceInfo;
import com.ean.project.model.entity.User;
import com.ean.project.model.enums.PostStatusEnum;
import com.ean.project.service.InterfaceInfoService;
import com.ean.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 帖子接口
 *
 * @author yupi
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private PovlApiClient povlApiClient;

    @Resource
    private UserService userService;

    // region 增删改查
    /**
     * 创建
     *
     * @param InterfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping ("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest InterfaceInfoAddRequest, HttpServletRequest request) {
        if (InterfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(InterfaceInfoAddRequest, interfaceInfo);
        // 校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, true);
        // 获取当前用户的ID
        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setUserId(loginUser.getId());
        boolean result = interfaceInfoService.save(interfaceInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newInterfaceInfoId = interfaceInfo.getId();
        return ResultUtils.success(newInterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping ("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param interfaceInfoUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest interfaceInfoUpdateRequest,
                                            HttpServletRequest request) {
        if (interfaceInfoUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo InterfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoUpdateRequest, InterfaceInfo);
        // 参数校验
        interfaceInfoService.validInterfaceInfo(InterfaceInfo, false);
        User user = userService.getLoginUser(request);
        long id = interfaceInfoUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = interfaceInfoService.updateById(InterfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<InterfaceInfo> getInterfaceInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        return ResultUtils.success(interfaceInfo);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<InterfaceInfo>> listInterfaceInfo(InterfaceInfoQueryRequest interfaceInfoQueryRequest) {
        InterfaceInfo InterfaceInfoQuery = new InterfaceInfo();
        if (interfaceInfoQueryRequest != null) {
            BeanUtils.copyProperties(interfaceInfoQueryRequest, InterfaceInfoQuery);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(InterfaceInfoQuery);
        List<InterfaceInfo> InterfaceInfoList = interfaceInfoService.list(queryWrapper);
        return ResultUtils.success(InterfaceInfoList);
    }

    /**
     * 分页获取列表
     *
     * @param interfaceInfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage(InterfaceInfoQueryRequest interfaceInfoQueryRequest, HttpServletRequest request) {
        if (interfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        // 获取分页数量
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        // 获取前端中的传输数据
        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();
        String content = interfaceInfoQueryRequest.getContent();
        // content 需支持模糊搜索
        interfaceInfoQueryRequest.setContent(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(interfaceInfoPage);
    }

    // 上线接口
    @PostMapping("/online")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> onlineInterfaceInfo(@RequestBody PostIdRequest postIdRequest) {
        if (postIdRequest == null || postIdRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = postIdRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceinfo = interfaceInfoService.getById(id);
        if (ObjectUtil.isEmpty(oldInterfaceinfo)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 判断该接口是否可以调用
        com.ean.client_sdk.model
                .User user = new com.ean.client_sdk.model.User();
        user.setUserAccount("hello");
        String username = povlApiClient.getUsernameByPost(user);
        if (StringUtils.isAnyBlank(username)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 将接口状态改为1
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(PostStatusEnum.ONLINE.getValue());
        boolean res = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(res);
    }


    @PostMapping("/offline")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> offlineInterfaceInfo(@RequestBody PostIdRequest postIdRequest){
        if (postIdRequest == null || postIdRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = postIdRequest.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceinfo = interfaceInfoService.getById(id);
        if (ObjectUtil.isEmpty(oldInterfaceinfo)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 将接口状态改为1
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(PostStatusEnum.OFFLINE.getValue());
        boolean res = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(res);
    }
    // endregion

}
