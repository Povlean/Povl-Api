package com.example.povlapi_flaten.controller;

import com.example.povlapi_flaten.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/name")
public class NameApiController {

    @GetMapping("/")
    public String getNameByGet(String name) {
        return "Get Name===>" + name;
    }

    @PostMapping("/")
    public String getNameByPost(@RequestParam String name) {
        return "Post Name===>" + name;
    }

    @PostMapping("/user")
    public String getUsernameByPost(@RequestBody User user) {
        return "Post Username===>" + user.getName();
    }

}
