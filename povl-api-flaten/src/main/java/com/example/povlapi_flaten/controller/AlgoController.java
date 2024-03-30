package com.example.povlapi_flaten.controller;

import com.ean.client_sdk.model.User;
import com.ean.client_sdk.utils.SignUtil;
import com.ean.client_sdk.model.Number;
import com.ean.commonapi.model.enums.ErrorCode;
import com.ean.commonapi.model.exception.BusinessException;
import com.example.povlapi_flaten.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.ean.client_sdk.constants.ApiConstant.*;
import static com.ean.client_sdk.constants.ApiConstant.BODY;

/**
 * @author:ean
 */
@Slf4j
@RestController
@RequestMapping("/algo")
public class AlgoController {

    @Resource
    private UserService userService;

    @PostMapping("/add")
    public Integer addNumber(@RequestBody @Validated Number number) {
        // 在用户调用接口信息表中，更新用户调用的次数
        return Integer.parseInt(number.getNumber1()) + Integer.parseInt(number.getNumber2());
    }

    @PostMapping("/minus")
    public Integer minusNumber(@RequestBody @Validated Number number) {
        // 在用户调用接口信息表中，更新用户调用的次数
        return Integer.parseInt(number.getNumber1()) - Integer.parseInt(number.getNumber2());
    }

    @PostMapping("/multi")
    public Integer multiNumber(@RequestBody @Validated Number number) {
        // 在用户调用接口信息表中，更新用户调用的次数
        return Integer.parseInt(number.getNumber1()) * Integer.parseInt(number.getNumber2());
    }

    @PostMapping("/divided")
    public Integer dividedNumber(@RequestBody @Validated Number number) {
        // 在用户调用接口信息表中，更新用户调用的次数
        if (Integer.parseInt(number.getNumber2()) != 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return Integer.parseInt(number.getNumber1()) / Integer.parseInt(number.getNumber2());
    }

}
