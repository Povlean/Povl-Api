package com.example.povlapi_flaten.controller;

import com.example.povlapi_flaten.model.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.example.povlapi_flaten.constants.ApiConstant.ACCESS_KEY;
import static com.example.povlapi_flaten.constants.ApiConstant.SECRET_KEY;

@RestController
@RequestMapping("/name")
public class NameApiController {

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
        String secretKey = request.getHeader(SECRET_KEY);
        if (!secretKey.equals("123456") || !accessKey.equals("123456")) {
            throw new RuntimeException();
        }
        return "Post Username===>" + user.getName();
    }

}
