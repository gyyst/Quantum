package com.lunarstra.quantum.circle;

import com.lunarstra.quantum.utils.RedisCache;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author gyyst
 * @Description
 * @Create by 2024/4/9 11:22
 */
@Component
public class PingRedis {
    @Resource
    private RedisCache redisCache;

    @Scheduled(cron = "0 0/30 * * * ?")
    public void pingRedis() {
        redisCache.setCacheObject("ping", "ping", 1, TimeUnit.MINUTES);
    }
}