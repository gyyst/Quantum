package com.lunarstra.quantum.constant;

/**
 * @author gyyst
 * @Description
 * @Create by 2023/4/3 19:47
 */

public interface RabbitQueueConstant {

    /**
     * 用户注册邮箱发送
     */
    String USER_SEND_EMAIL = "USER.EMAIL.SEND";
    /**
     * 用户注册邮箱发送验证码
     */
    String USER_SEND_CODE = "USER.EMAIL.SEND.CODE";
    /**
     * 向用户发送硬币
     */
    String USER_SEND_COIN = "USER.SEND.COIN";
    /**
     * 用户使用硬币
     */
    String USER_SUB_COIN = "USER.SUB.COIN";

    String GEN_CHART_QUEUE = "GEN_CHART_QUEUE";

    String RUN_CODE_QUEUE = "RUN_CODE_QUEUE";
}
