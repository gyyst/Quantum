package com.lunarstra.quantum.constant.column;

import lombok.Getter;

/**
 * @author gyyst
 * @Description
 * @Create by 2023/4/3 19:05
 */
@Getter
public enum ColumnEnum {

    /**
     * 专栏正在审核
     */
    COLUMN_STATE_PROCESS("004000", "审核中"),
    /**
     * 专栏开放
     */
    COLUMN_STATE_NORMAL("004001", "正常"),
    /**
     * 专栏封禁
     */
    COLUMN_STATE_BANNED("004002", "封禁"),
    /**
     * 专栏等待审核
     */
    COLUMN_WAIT_EXAMINE("004003", "待审核"),
    /**
     * 审核完未通过
     */
    COLUMN_NOT_PASS("004004", "审核未通过"),
    /**
     * 审核完未通过
     */
    COLUMN_WAIT_DELIVER_QUEUE("004005", "待审核");

    /**
     * code
     */
    private final String code;

    /**
     * message
     */
    private final String message;

    ColumnEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
