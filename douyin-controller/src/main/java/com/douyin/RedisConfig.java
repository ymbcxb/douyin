package com.douyin;


import com.douyin.pojo.Users;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author ymbcxb
 * @title
 * @Package com.douyin
 * @date 2019/7/24 21:04
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Users> redisTemplate(RedisConnectionFactory cf){
        RedisTemplate<String,Users> redis = new RedisTemplate<String,Users>();
        redis.setConnectionFactory(cf);
        redis.setKeySerializer(new StringRedisSerializer());
        redis.setValueSerializer(new Jackson2JsonRedisSerializer<Users>(Users.class));
        return redis;
    }
}
