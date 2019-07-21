package com.douyin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author ymbcxb
 * @title
 * @Package com.douyin
 * @date 2019/7/20 20:27
 */
@ComponentScan(basePackages ={"com.douyin"})
@EnableAutoConfiguration
@MapperScan(basePackages = "com.douyin.mapper")
public class DouyinApplication {
    public static void main(String[] args) {
        SpringApplication.run(DouyinApplication.class,args);
    }
}
