package com.lunarstra.quantum.cache;

import com.lunarstra.quantum.constant.RedisConstant;
import com.lunarstra.quantum.utils.RedisCache;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author gyyst
 * @Description
 * @Create by 2025/10/26 23:52
 */
@Component
public class UserCache {
    @Resource
    private RedisCache redisCache;

    /**
     * 缓存用户注册验证码
     *
     * @param address
     * @param code
     */
    public void cacheValidCode(String address, String code) {
        redisCache.setCacheObject(RedisCache.buildKey(RedisConstant.USER_REGISTER_EMAIL, address), code,
            RedisConstant.USER_REGISTER_EMAIL_TTL_S, TimeUnit.SECONDS);
    }
}
