package com.douyin.service;

import com.douyin.common.ServerResponse;
import com.douyin.pojo.Bgm;

import java.util.List;

/**
 * @author ymbcxb
 * @title
 * @Package com.douyin.service
 * @date 2019/7/25 9:55
 */
public interface BgmService {

    ServerResponse<List<Bgm>> getBgmList();
}
