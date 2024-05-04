package com.akali.security.controller;

import com.akali.common.model.RestResponseObject;
import com.akali.common.utils.JSONUtils;
import com.akali.security.model.vo.UserLoginReq;
import com.akali.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    /**
     * 用户登录
     */
    @PostMapping("/login")
    public RestResponseObject userLogin(@RequestBody UserLoginReq userLoginReq) {
        log.info("接收到用户登录请求: req: {}", JSONUtils.toJson(userLoginReq));
        return userService.login(userLoginReq);
    }

    @PostMapping("/getUser111")
    public String getUser(@RequestBody UserLoginReq userLoginReq) {
        return userLoginReq.getUsername() + ":" + "Tom";
    }

    @PostMapping("/logout")
    public RestResponseObject logout() {
        log.info("接收到用户退出登录请求");
        return userService.logout();
    }
}
