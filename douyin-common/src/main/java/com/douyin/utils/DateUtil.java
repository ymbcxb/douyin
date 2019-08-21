package com.douyin.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author ymbcxb
 * @title
 * @Package com.douyin.utils
 * @date 2019/8/21 16:46
 */
@Slf4j
public class DateUtil {
    public static String parseTime(Date currentDate,Date date){
        long start = currentDate.getTime();
        long end = date.getTime();
        long rs = end - start;
        if(rs/1000 < 60){
            //秒
            return String.valueOf(rs / 1000)+"秒以前";
        }else if(rs/1000/60 < 60){
            //分
            return String.valueOf(rs/1000/60)+"分钟以前";
        }else if(rs/1000/60/60 <24){
            //小时
            return String.valueOf(rs/1000/60/60)+"小时以前";
        }else{
            //天
            return String.valueOf(rs/1000/60/60/24)+"天以前";
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Date d1 = new Date();
        TimeUnit.MINUTES.sleep(5);
        Date d2 = new Date();
        System.out.println(parseTime(d1,d2));
    }
}
