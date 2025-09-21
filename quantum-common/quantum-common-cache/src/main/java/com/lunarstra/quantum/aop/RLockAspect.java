package com.lunarstra.quantum.aop;

import com.lunarstra.quantum.annotation.MyRLock;
import jakarta.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

//@Aspect
//@Component
public class RLockAspect {

    @Resource
    private StringRedisTemplate redisTemplate;

    @Resource
    private RedissonClient redissonClient;

    /**
     * joinPoint.getArgs(); 获取方法参数
     * joinPoint.getTarget().getClass(); 获取目标类
     *
     * @param joinPoint
     * @return
     */
    @Around("@annotation(com.vivi.cloud.annotation.MyRLock)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取切点方法的签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取方法对象
        Method method = signature.getMethod();
        // 获取方法的返回值类型
        //        Class<?> returnType = method.getReturnType();

        // 获取方法上指定注解的对象
        MyRLock annotation = method.getAnnotation(MyRLock.class);
        int waitTime = annotation.waitTime();
        int leaseTime = annotation.leaseTime();
        TimeUnit unit = annotation.unit();
        // 获取注解中的前缀
        String lockName = annotation.lockName();
        RLock lock = redissonClient.getLock(lockName);
        try {
            if (lock.tryLock(waitTime, leaseTime, unit)) {
                Object result = joinPoint.proceed(joinPoint.getArgs());
                return result;
            } else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            throw new RuntimeException("操作太快了");
        } finally {
            lock.unlock();
        }
    }
}
