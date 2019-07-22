package com.douyin.service;

import com.douyin.common.ServerResponse;
import com.douyin.pojo.Users;

/**
 * @author ymbcxb
 * @title
 * @Package com.douyin.service
 * @date 2019/7/22 0:24
 */
public interface UserService {

    ServerResponse register(String username,String password);

    ServerResponse login(String username,String password);
}
