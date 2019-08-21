package com.douyin.vo;

import lombok.Data;

/**
 * @author ymbcxb
 * @title
 * @Package com.douyin.vo
 * @date 2019/8/21 14:31
 */
@Data
public class CommentsVo {
    private String id;
    private String fromUserId;
    private String faceImg;
    private String nickName;
    private String timeAgoStr;
    private String toNickname;
    private String comment;
}
