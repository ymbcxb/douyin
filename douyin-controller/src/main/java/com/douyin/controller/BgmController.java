package com.douyin.controller;

import com.douyin.common.ServerResponse;
import com.douyin.pojo.Bgm;
import com.douyin.service.BgmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ymbcxb
 * @title
 * @Package com.douyin.controller
 * @date 2019/7/25 9:58
 */
@RestController
@RequestMapping("/bgm/")
@Api(value = "背景音乐",tags = {"背景音乐"})
public class BgmController {
    @Autowired
    private BgmService bgmService;

    /**
     * 获取所有BGM
     * @return
     */
    @ApiOperation(value = "获取所有背景音乐",notes = "获取所有背景音乐")
    @GetMapping("getBgmList")
    public ServerResponse<List<Bgm>> getBgmList(){
        return bgmService.getBgmList();
    }
}
