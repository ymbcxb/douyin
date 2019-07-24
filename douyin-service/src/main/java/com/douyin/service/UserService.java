package com.douyin.service;

import com.douyin.common.ServerResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ymbcxb
 * @title
 * @Package com.douyin.service
 * @date 2019/7/22 0:24
 */
public interface UserService {

    ServerResponse register(String username,String password);

    ServerResponse login(String username,String password);

    ServerResponse logut(String userId);

    ServerResponse uploadFace(String userId, MultipartFile file);

    ServerResponse getUserInfo(String userId);
}
