package com.lunarstra.quantum.aop;

import com.lunarstra.quantum.annotation.RedisLimit;
import com.lunarstra.quantum.common.BaseResponse;
import com.lunarstra.quantum.common.ErrorCode;
import com.lunarstra.quantum.manager.RedisLimiterManager;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Simons
 */
@Component
@Scope
@Aspect
@Slf4j
public class RateLimitAspect {
    @Resource
    private RedisLimiterManager redisLimiterManager;

    @Around("@annotation(com.lunarstra.quantum.annotation.RedisLimit)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        RedisLimit annotation = method.getAnnotation(RedisLimit.class);
        if (redisLimiterManager.rateLimit(method, annotation)) {
            return pjp.proceed();
        }
        return BaseResponse.error(ErrorCode.SYSTEM_ERROR, annotation.limitMsg());
    }
}
