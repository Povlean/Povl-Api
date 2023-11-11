package com.example.povlapi_flaten.controller;

import com.ean.client_sdk.model.User;
import com.ean.client_sdk.utils.SignUtil;
import com.example.povlapi_flaten.service.UserService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import static com.ean.client_sdk.constants.ApiConstant.*;


@RestController
@RequestMapping("/name")
public class NameApiController {
    @Resource
    private UserService userService;

    @GetMapping("/get")
    public String getNameByGet(String name) {
        return "Get Name===>" + name;
    }

    @PostMapping("/post")
    public String getNameByPost(@RequestParam String name) {
        return "Post Name===>" + name;
    }

    @PostMapping("/user")
    public String getUsernameByPost(@RequestBody User user, HttpServletRequest request) {
        String accessKey = request.getHeader(ACCESS_KEY);
        String nonce = request.getHeader(NONCE);
        String receiveSign = request.getHeader(SIGN);
        String body = request.getHeader(BODY);
        // 从实现类中查出数据库中的用户
        User currentUser = userService.getUserByUserAccount(user.getUserAccount());
        // 从数据库中取出AK进行校验
        if (!accessKey.equals(currentUser.getAccessKey())) {
            throw new RuntimeException("无权限");
        }
        if (Long.parseLong(nonce) > 10000) {
            throw new RuntimeException("无权限");
        }
        String metaSign = SignUtil.getSign(body, currentUser.getSecretKey());
        if (!receiveSign.equals(metaSign)) {
            throw new RuntimeException("签名有误");
        }
        String res = "Post userAccount===>" + user.getUserAccount();
        // 在用户调用接口信息表中，更新用户调用的次数

        return res;
    }

}
