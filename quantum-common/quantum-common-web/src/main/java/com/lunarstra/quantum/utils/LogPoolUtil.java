package com.lunarstra.quantum.utils;

import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author gyyst
 * @Description
 * @Create by 2024/10/5 15:41
 */
@Component
public class LogPoolUtil {

    private static final ThreadPoolExecutor LOG_RECORD_POOL = null;

    public void log(Runnable r) {
        LOG_RECORD_POOL.submit(r);
    }

    public <V> Future<V> log(Callable<V> c) {
        return LOG_RECORD_POOL.submit(c);
    }

    public <V> V logWait(Callable<V> c) {
        try {
            return LOG_RECORD_POOL.submit(c).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}