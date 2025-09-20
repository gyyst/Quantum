package com.lunarstra.quantum.constant.user;

import lombok.Getter;

/**
 * @author gyyst
 * @Description
 * @Create by 2023/4/6 10:14
 */
@Getter
public enum UserEnum {
    /**
     * 用户开放
     */
    USER_STATE_NORMAL("001000", "正常"),
    /**
     * 用户封禁
     */
    USER_STATE_BANNED("001001", "封禁");

    /**
     * code
     */
    private final String code;

    /**
     * message
     */
    private final String message;

    UserEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
