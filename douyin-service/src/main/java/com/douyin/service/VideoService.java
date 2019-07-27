package com.douyin.service;

import com.douyin.common.ServerResponse;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ymbcxb
 * @title
 * @Package com.douyin.service
 * @date 2019/7/25 13:33
 */
public interface VideoService {

    ServerResponse uploadVideo(String BgmId,String userId, MultipartFile multipartFile, String videoDesc, Double videoSeconds,
                               Integer videoWidth, Integer videoHeight);

    ServerResponse<PageInfo> videoList(Integer pageNum, Integer pageSize);
}
