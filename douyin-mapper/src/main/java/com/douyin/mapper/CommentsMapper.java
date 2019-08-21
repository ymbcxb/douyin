package com.douyin.mapper;

import com.douyin.pojo.Comments;
import com.douyin.utils.MyMapper;
import com.douyin.vo.CommentsVo;

import java.util.List;

public interface CommentsMapper extends MyMapper<Comments> {
    List<CommentsVo> selectCommentsVoList(String videoId);
}