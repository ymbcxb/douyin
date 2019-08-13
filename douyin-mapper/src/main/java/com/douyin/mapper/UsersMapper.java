package com.douyin.mapper;

import com.douyin.pojo.Users;
import com.douyin.utils.MyMapper;

import java.util.List;

public interface UsersMapper extends MyMapper<Users> {
    List<Users> findFollowuserByUserId(String userId);
}