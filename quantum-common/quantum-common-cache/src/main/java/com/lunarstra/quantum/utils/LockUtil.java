package com.lunarstra.quantum.utils;

import jakarta.annotation.Resource;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gyyst
 * @Description
 * @Create by 2024/10/3 22:13
 */
@Component
public class LockUtil {
    @Resource
    private RedissonClient redissonClient;
    private ReentrantLock reentrantLock;

    /**
     * 获取锁并进行操作
     *
     * @param lockName
     * @param getLock
     * @param failLock
     */
    public void lockRun(String lockName, Runnable getLock, Runnable failLock) {
        RLock lock = redissonClient.getLock(lockName);
        boolean tryLock = lock.tryLock();
        if (tryLock) {
            try {
                getLock.run();
            } finally {
                lock.unlock();
            }
        } else {
            if (failLock != null)
                failLock.run();
        }
    }

    /**
     * 获取锁并进行操作
     *
     * @param lock
     * @param getLock
     */
    public void localLockRun(String lock, Runnable getLock) {
        synchronized (lock.intern()) {
            getLock.run();
        }

    }

    /**
     * 获取锁并进行操作
     *
     * @param lockName
     * @param getLock
     * @param failValue
     */
    public <V> V lockRun(String lockName, Callable<V> getLock, V failValue) {
        RLock lock = redissonClient.getLock(lockName);
        boolean tryLock = lock.tryLock();
        if (tryLock) {
            try {
                return getLock.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
        return failValue;
    }

    /**
     * 获取锁并进行操作
     *
     * @param lockName
     * @param getLock
     * @param failLock
     */
    public <V> V lockRun(String lockName, Callable<V> getLock, Runnable failLock, V failValue) {
        RLock lock = redissonClient.getLock(lockName);
        boolean tryLock = lock.tryLock();
        if (tryLock) {
            try {
                return getLock.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        } else {
            if (failLock != null)
                failLock.run();

        }
        return failValue;
    }

    /**
     * 获取锁并进行操作
     *
     * @param lockName
     * @param getLock
     */
    public void lockRun(String lockName, Runnable getLock) {
        lockRun(lockName, getLock, null);
    }
}