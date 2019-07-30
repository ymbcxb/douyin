package com.douyin.mapper;

import com.douyin.pojo.UsersLikeVideos;
import com.douyin.utils.MyMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

public interface UsersLikeVideosMapper extends MyMapper<UsersLikeVideos> {
    @Delete("delete from users_like_videos where user_id = #{userId} and video_id = #{videoId}")
    Integer deleteByUserIdAndVideoId(@Param("userId")String userId,@Param("videoId")String videoId);
}