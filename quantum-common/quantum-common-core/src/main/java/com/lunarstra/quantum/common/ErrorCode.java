package com.lunarstra.quantum.common;

import lombok.Getter;

/**
 * 错误码
 *
 * @author gyyst
 */
@Getter
public enum ErrorCode {

    SUCCESS(0, "ok"),
    ASKEY_ERROR(401, "AK与SK错误"),

    PARAMS_ERROR(40000, "请求参数错误"),
    DATA_EXITS(40001, "数据已存在"),
    DATA_NOT_EXITS(40002, "数据已存在"),
    PASSWORD_ERROR(40003, "密码错误"),
    NOT_LOGIN_ERROR(40100, "未登录"),
    NO_AUTH_ERROR(40101, "无权限"),
    FORBIDDEN_ERROR(40300, "禁止访问"),
    OPERATE_FAST(40301, "您的操作太快了，请稍后重试"),

    NOT_FOUND_ERROR(40400, "请求数据不存在"),
    ASKEY_EXIST_ERROR(40401, "AK与SK已存在，请先删除后再申请"),
    BUCKET_NOT_EMPTY_ERROR(40402, "bucket非空，无法删除bucket"),
    FILE_IO_ERROR(40403, "文件io异常"),
    MINIO_SERVER_ERROR(40404, "文件服务器异常"),
    MINIO_INSUFFICIENT_DATA(40405, "文件服务器资源不足"),
    FILE_TYPE_ERROR(40406, "文件类型错误"),

    SYSTEM_ERROR(50000, "系统内部异常"),
    OPERATION_ERROR(50001, "操作失败"),
    LESS_BALANCE(50002, "余额不足"),
    JUDGE_ERROR(50003, "未通过判题用例");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}