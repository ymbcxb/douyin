package com.douyin.mapper;

import com.douyin.pojo.SearchRecords;
import com.douyin.utils.MyMapper;

import java.util.List;

/**
 * @author ymbcxb
 * @title
 * @Package com.douyin.mapper
 * @date 2019/7/27 11:00
 */
public interface SearchRecordsMapper extends MyMapper<SearchRecords> {
    List<String> getHotSearchRecord(Integer num);
}
