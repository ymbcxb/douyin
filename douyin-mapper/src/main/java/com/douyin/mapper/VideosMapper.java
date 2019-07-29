package com.douyin.mapper;

import com.douyin.pojo.Videos;
import com.douyin.utils.MyMapper;
import com.douyin.vo.VideosVo;

import java.util.List;

public interface VideosMapper extends MyMapper<Videos> {

    List<VideosVo> selectAllVideosByValue(String value);

}