package com.lunarstra.quantum.config;

import jakarta.annotation.Resource;
import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redisson 配置
 *
 * @author gyyst
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedisConfig {

    private String host;

    private String port;

    private String password;

    @Resource
    private RedisTemplate redisTemplate;

    @Bean
    public RedissonClient redissonClient() {
        // 1. 创建配置
        Config config = new Config();
        String redisAddress = String.format("redis://%s:%s", host, port);
        config.useSingleServer()
            .setAddress(redisAddress)
            .setPassword(password)
            .setDatabase(0)
            .setConnectionMinimumIdleSize(1)
            .setConnectionPoolSize(4)
            .setSubscriptionConnectionMinimumIdleSize(1)
            .setSubscriptionConnectionPoolSize(4);
        // 2. 创建实例
        return Redisson.create(config);
    }

    @Bean
    public RedisTemplate redisTemplateInit() {
        //设置序列化Key的实例化对象
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置序列化Value的实例化对象
        redisTemplate.setValueSerializer(new SafeGenericJackson2JsonRedisSerializer());

        // 设置Hash的key和value序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new SafeGenericJackson2JsonRedisSerializer());

        return redisTemplate;
    }
}