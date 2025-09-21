package com.lunarstra.quantum.constant;

/**
 * @author gyyst
 * @Description
 * @Create by 2023/4/8 23:16
 */
public interface LockConstant {
    /**
     * lock:columnPraise:columnId:userId
     */
    String COLUMN_PRAISE = "lock:columnPraise:";
    /**
     * lock:user:sign:userId
     */
    String USER_SIGN = "lock:user:sign:";

    /**
     * lock:api:userId:apiId
     */
    String PAY_API = "lock:api:";
    /**
     * lock:api:check:task
     */
    String API_CHECK_TASK = "lock:api:check:task";
    /**
     * lock:api:check:online:apiId
     */
    String API_CHECK_ONLINE = "lock:api:check:online:";

    /**
     * lock:user:coin:userId
     */
    String USER_COIN = "lock:user:coin:";
    /**
     * lock:column:coin:columnId
     */
    String COLUMN_COIN = "lock:column:coin:";
    /**
     * task:column:sync
     * 锁任务的，从mysql中同步专栏到es中
     */
    String TASK_COLUMN_SYNC = "task:column:sync";
    /**
     * 管理员专栏审核任务派发公平锁
     */
    String MANAGER_COLUMN_EXAMINE_DISTRIBUTE = "fair:lock:manager:column:distribute:";

    /**
     * task:column:wait:examine:queue
     * 锁任务，将待审任务放入审核队列中
     */
    String TASK_COLUMN_TO_WAIT_EXAMINE_QUEUE = "task:column:wait:examine:queue";

    /**
     * 数据字典缓存表锁
     */
    String MAP_DATA_DICTIONARY = "lock:map:data:dictionary";
}