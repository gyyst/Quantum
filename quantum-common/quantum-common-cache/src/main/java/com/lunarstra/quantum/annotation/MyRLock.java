package com.lunarstra.quantum.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author gyyst
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRLock {

    /**
     * 锁名
     *
     * @return
     */
    String lockName() default "lock:";

    /**
     * 设置缓存的有效时间
     * 单位：秒
     *
     * @return
     */
    int waitTime() default 1;

    /**
     * 防止雪崩设置的随机值范围
     * 单位：秒
     *
     * @return
     */
    int leaseTime() default 5;

    /**
     * 时间单位
     *
     * @return
     */
    TimeUnit unit() default TimeUnit.SECONDS;

}
