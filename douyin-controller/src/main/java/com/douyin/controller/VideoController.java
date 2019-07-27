package com.douyin.controller;

import com.douyin.common.ServerResponse;
import com.douyin.service.VideoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ymbcxb
 * @title
 * @Package com.douyin.controller
 * @date 2019/7/25 12:38
 */
@RestController
@Api(value = "视频接口",tags = {"视频接口"})
@RequestMapping("/video/")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @ApiOperation(value = "上传视频",notes = "上传视频")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户Id", paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "bgmId",value = "bgmId",paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "videoDesc",value = "视频详情",paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "videoSeconds",value = "视频时长",paramType = "form",dataType = "Double"),
            @ApiImplicitParam(name = "videoWidth",value = "视频宽度",paramType = "form",dataType = "Integer"),
            @ApiImplicitParam(name = "videoHeight",value = "视频高度",paramType = "form",dataType = "Integer"),
    })
    @PostMapping(value = "upload",headers = "content-type=multipart/form-data")
    public ServerResponse upload(String userId,
                                 @ApiParam(value = "短视频") @RequestParam("file") MultipartFile file,
                                 String videoDesc,Double videoSeconds,Integer videoWidth,
                                 Integer videoHeight,String bgmId){
        return videoService.uploadVideo(bgmId,userId, file, videoDesc, videoSeconds, videoWidth,videoHeight);
    }


    @ApiOperation(value = "查询所有视频",notes = "查询所有视频")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页数", paramType = "form",dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize",value = "页大小",paramType = "form",dataType = "Integer"),
    })
    @PostMapping(value = "list")
    public ServerResponse list(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        return videoService.videoList(pageNum, pageSize);
    }
}
