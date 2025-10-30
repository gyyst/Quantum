package com.lunarstra.quantum.manager;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.lunarstra.quantum.annotation.RedisLimit;
import com.lunarstra.quantum.constant.LimitType;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * @author gyyst
 * @Description
 * @Create by 2023/7/3 22:00
 */

@Component
public class RedisLimiterManager {
    @Resource
    private RedissonClient redissonClient;

    public boolean rateLimit(Method method, RedisLimit annotation) {
        long period = annotation.time();
        long count = annotation.count();
        String limitType = annotation.limitType();
        String key = annotation.key();
        String userId = StpUtil.isLogin() ? StpUtil.getLoginId().toString() : "defaultUserId";
        switch (limitType) {
            case LimitType.USER -> key = key + userId;
            case LimitType.IP -> key = key + getIpAddress();
            case LimitType.IP_WITH_METHOD -> key = key + getIpAddress() + ":" + StrUtil.upperFirst(method.getName());
            case LimitType.CUSTOM -> {
            }
            default -> key = key + StrUtil.upperFirst(method.getName());
        }
        String keys = StrUtil.concat(true, annotation.redisKeyPrefix().startsWith("limit:")
            ? annotation.redisKeyPrefix() + ":"
            : "limit:" + annotation.redisKeyPrefix() + ":", limitType, key);
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(keys);
        if (!rateLimiter.isExists()) {
            rateLimiter.trySetRate(RateType.OVERALL, count, Duration.of(annotation.time(), ChronoUnit.SECONDS));
        }
        return rateLimiter.tryAcquire();
    }

    public boolean rateLimit(long count, long period, String limitType, String key, String keyPrefix,
        String methodName) {
        switch (limitType) {
            case LimitType.USER -> key = key + StpUtil.getLoginIdAsLong();
            case LimitType.CUSTOM -> {
            }
            case LimitType.IP -> key = key + getIpAddress();
            case LimitType.IP_WITH_METHOD -> key = key + getIpAddress() + ":" + StrUtil.upperFirst(methodName);
            default -> key = key + StrUtil.upperFirst(methodName);
        }
        String keys = StrUtil.concat(true,
            keyPrefix.startsWith("limit:") ? keyPrefix + ":" : "limit:" + keyPrefix + ":", limitType, key);
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(keys);
        rateLimiter.trySetRate(RateType.OVERALL, count, period, RateIntervalUnit.SECONDS);
        return rateLimiter.tryAcquire();
    }

    public String getIpAddress() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(
            RequestContextHolder.getRequestAttributes())).getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}