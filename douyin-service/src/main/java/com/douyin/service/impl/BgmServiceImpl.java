package com.douyin.service.impl;

import com.douyin.common.ServerResponse;
import com.douyin.mapper.BgmMapper;
import com.douyin.pojo.Bgm;
import com.douyin.service.BgmService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ymbcxb
 * @title
 * @Package com.douyin.service.impl
 * @date 2019/7/25 9:55
 */
@Service
public class BgmServiceImpl implements BgmService {

    @Autowired
    private BgmMapper bgmMapper;

    @Override
    public ServerResponse<List<Bgm>> getBgmList(){
        List<Bgm> bgmList = bgmMapper.selectAll();
        if(bgmList == null){
            return ServerResponse.createByErrorMessage("没有BGM", Lists.newArrayList());
        }
        return ServerResponse.createBySuccess(bgmList);
    }
}
