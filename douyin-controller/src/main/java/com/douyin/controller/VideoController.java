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
            @ApiImplicitParam(name = "value",value = "根据某个值",paramType = "form",dataType = "String"),
    })
    @PostMapping(value = "list")
    public ServerResponse list(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                               String value) {
        return videoService.videoList(pageNum, pageSize,value);
    }

    @ApiOperation(value = "根据关键字搜索视频",notes = "根据关键字搜索视频")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "value",value = "关键字", paramType = "form",dataType = "String")})
    @PostMapping(value="search")
    public ServerResponse search(String value){
        return videoService.search(value);
    }

    /**
     * 获取热点记录
     * @return
     */
    @ApiOperation(value = "获取热点记录",notes = "获取热点记录")
    @ApiImplicitParam(name = "num",value = "条数", paramType = "query",dataType = "Integer")
    @PostMapping(value="hot")
    public ServerResponse hot(Integer num){
        return videoService.getHot(num);
    }


    @ApiOperation(value = "喜欢或者不喜欢该视频",notes = "喜欢或者不喜欢视频")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户Id", paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "videoId",value = "视频Id", paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "videoCreaterId",value = "创建视频用户Id", paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "likeFlag",value = "喜欢是否,1为喜欢，0为不喜欢", paramType = "query",dataType = "Integer")
        }
    )
    @PostMapping("likeVideoOrNot")
    public ServerResponse likeVideoOrNot(String userId, String videoId, String videoCreaterId,Integer likeFlag){
        return videoService.likeVideoOrNot(userId,videoId,videoCreaterId,likeFlag);
    }

    @ApiOperation(value = "判断用户是否喜欢这个视频",notes = "判断用户是否喜欢这个视频")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "videoId", value = "视频Id", paramType = "query", dataType = "String")
    })
    @PostMapping("likeVideo")
    public ServerResponse likeVideo(String userId,String videoId){
        return videoService.likeVideo(userId,videoId);
    }
}
