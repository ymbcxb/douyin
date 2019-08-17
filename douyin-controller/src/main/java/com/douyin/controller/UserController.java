package com.douyin.controller;

import com.douyin.common.ServerResponse;
import com.douyin.pojo.Users;
import com.douyin.service.UserService;
import com.douyin.service.VideoService;
import io.swagger.annotations.*;
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
    @Autowired
    private VideoService videoService;

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
    @ApiOperation(value = "获取用户信息",notes = "获取用户信息")
    @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "String", paramType = "query")
    @PostMapping("getUserInfo")
    public ServerResponse getUserInfo(String userId){
        return userService.getUserInfo(userId);
    }


    /**
     * 互相关注
     * @param userId
     * @param fanId
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fanId", value = "粉丝Id", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping("/follow")
    public ServerResponse follow(String userId,String fanId,Integer type){
        return userService.follow(userId,fanId,type);
    }

    /**
     * 根据类型获取用户信息
     * @param userId
     * @param type
     * @return
     */
    @GetMapping("userInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型", required = true, dataType = "String", paramType = "query")
    })
    public ServerResponse getUserByType(String userId,String type){
        ServerResponse rs = new ServerResponse();
        if("video".equals(type)){
            rs = videoService.videoList(userId);
        }else if("collection".equals(type)){
            rs = videoService.videoLikeList(userId);
        }else if("follow".equals(type)){
            rs = userService.followList(userId);
        }
        return rs;
    }

    @GetMapping("userFollowStatus")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fanId", value = "粉丝Id", required = true, dataType = "String", paramType = "query")
    })
    public ServerResponse userFollowStatus(String userId,String fanId){
        return userService.userStatus(userId,fanId);
    }

}
