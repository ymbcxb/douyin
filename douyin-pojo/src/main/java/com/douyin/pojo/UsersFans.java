package com.douyin.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "users_fans")
public class UsersFans {
    @Id
    private String id;

    /**
     * 用户
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 粉丝
     */
    @Column(name = "fan_id")
    private String fanId;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取用户
     *
     * @return user_id - 用户
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户
     *
     * @param userId 用户
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取粉丝
     *
     * @return fan_id - 粉丝
     */
    public String getFanId() {
        return fanId;
    }

    /**
     * 设置粉丝
     *
     * @param fanId 粉丝
     */
    public void setFanId(String fanId) {
        this.fanId = fanId == null ? null : fanId.trim();
    }
}