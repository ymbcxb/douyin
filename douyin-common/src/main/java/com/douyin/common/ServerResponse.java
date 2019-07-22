package com.douyin.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @author ymbcxb
 * @title
 * @Package com.douyin.common
 * @date 2019/7/22 0:02
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerResponse<T> implements Serializable {

    private Integer status;
    private String msg;
    private T data;

    //构造器

    public ServerResponse() {
    }

    private ServerResponse(Integer status) {
        this.status = status;
    }

    private ServerResponse(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    //成功
    public static <T> ServerResponse<T> createBySuccess() {
        return new ServerResponse<T>(Const.SUCCESS_CODE);
    }

    public static <T> ServerResponse<T> createBySuccessMessage(String msg) {
        return new ServerResponse<T>(Const.SUCCESS_CODE, msg);
    }

    public static <T> ServerResponse<T> createBySuccess(T data) {
        return new ServerResponse<T>(Const.SUCCESS_CODE, data);
    }

    public static <T> ServerResponse<T> createBySuccessMessage(String msg, T data) {
        return new ServerResponse<T>(Const.SUCCESS_CODE, msg, data);
    }

    //失败

    public static <T> ServerResponse<T> createByError() {
        return new ServerResponse<T>(Const.ERROR_CODE);
    }

    public static <T> ServerResponse<T> createByErrorMessage(String msg) {
        return new ServerResponse<T>(Const.ERROR_CODE, msg);
    }

    public static <T> ServerResponse<T> createByError(T data) {
        return new ServerResponse<T>(Const.ERROR_CODE, data);
    }

    public static <T> ServerResponse<T> createByErrorMessage(String msg, T data) {
        return new ServerResponse<T>(Const.ERROR_CODE, msg, data);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean success() {
        return this.status.equals(Const.SUCCESS_CODE);
    }
}
