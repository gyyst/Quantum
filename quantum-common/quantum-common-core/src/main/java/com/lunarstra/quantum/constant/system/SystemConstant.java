package com.lunarstra.quantum.constant.system;

/**
 * @author gyyst
 * @Description
 * @Create by 2023/4/2 12:15
 */
public interface SystemConstant {
    String SALT = "gyyst12345678910";
    // 链路ID 会记录整个链路
    String TRACE_ID = "traceId";
    // 区域ID 会记录当前系统
    String SPAN_ID = "spanId";
    // 系统变量名称,用于获取AES密钥
    String AES_ENV_KEY = "AES_ENCRYPTION_KEY";
}
