package com.lunarstra.quantum.annotation;

import com.lunarstra.quantum.constant.LimitType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gyyst
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLimit {
    /**
     * 限流key
     */
    String key() default "";

    /**
     * 限流key
     */
    String redisKeyPrefix() default "limit:";

    /**
     * 限流时间,单位秒
     */
    long time() default 1;

    /**
     * 限流次数
     */
    long count() default 100;

    // 限流类型
    String limitType() default LimitType.IP_WITH_METHOD;

    /**
     * 限流后返回的文字
     */
    String limitMsg() default "系统繁忙，请稍后再试";
}