package com.douyin.controller;

import com.douyin.common.ServerResponse;
import com.douyin.pojo.Users;
import com.douyin.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ymbcxb
 * @title
 * @Package com.douyin.com.douyin.controller
 * @date 2019/7/22 0:08
 */
@RestController
@RequestMapping("/user/")
@Api(value = "用户接口",tags = "用户接口")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册
     * @param user
     * @return
     */
    @ApiOperation(value = "用户注册", notes = "用户接口")
    @PostMapping("register")
    public ServerResponse register(@RequestBody Users user) {
        return userService.register(user.getUsername(), user.getPassword());
    }


    /**
     * 登陆
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "用户登陆", notes = "用户接口")
    @PostMapping("login")
    public ServerResponse login(@RequestBody Users user) {
        return userService.login(user.getUsername(), user.getPassword());
    }

    /**
     * 注销
     *
     * @param
     * @return
     */
    @ApiOperation(value = "用户注销", notes = "用户接口")
    @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "String", paramType = "query")
    @PostMapping("logut")
    public ServerResponse logout(String userId) {
        return userService.logut(userId);
    }

    /**
     * 上传头像
     * @param userId
     * @param file
     * @return
     */
    @ApiOperation(value = "用户上传头像",notes = "用户上传头像的接口")
    @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "String", paramType = "query")
    @PostMapping(value = "uploadFace",headers = "content-type=multipart/form-data")
    public ServerResponse uploadFace(String userId,
                                     @ApiParam(name = "file",value = "上传的头像",required = true) @RequestParam(value = "file",required = true)MultipartFile file){
        return userService.uploadFace(userId,file);
    }


    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取用户信息",notes = "用户上传头像的接口")
    @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "String", paramType = "query")
    @PostMapping("getUserInfo")
    public ServerResponse getUserInfo(String userId){
        return userService.getUserInfo(userId);
    }


}
