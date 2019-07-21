package com.douyin.utils;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author ymbcxb
 * @title
 * @Package com.douyin.utils
 * @date 2019/7/20 21:25
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}