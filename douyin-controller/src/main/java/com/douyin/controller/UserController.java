package com.douyin.controller;

import com.douyin.common.ServerResponse;
import com.douyin.pojo.Users;
import com.douyin.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.concurrent.TimeUnit;

/**
 * @author ymbcxb
 * @title
 * @Package com.douyin.com.douyin.controller
 * @date 2019/7/22 0:08
 */
@RestController
@RequestMapping("/user/")
@Api(value = "用户的接口")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册
     * @param user
     * @return
     */
    @ApiOperation(value = "用户注册",notes = "用户接口")
    @PostMapping("register")
    public ServerResponse register(@RequestBody Users user){
        return userService.register(user.getUsername(),user.getPassword());
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @ApiOperation(value = "用户登陆",notes = "用户接口")
    @PostMapping("login")
    public ServerResponse login(@RequestBody Users user){
        return userService.login(user.getUsername(),user.getPassword());
    }
}
