package com.lunarstra.quantum.strategy.validcode;

import com.lunarstra.quantum.model.enums.RegisterEnum;

/**
 * @author gyyst
 * @Description
 * @Create by 2025/10/26 21:24
 */

public abstract class AbstractValidCodeSender {
    /**
     * 获取注册方式
     *
     * @return
     */
    public abstract RegisterEnum getType();

    /**
     * 发送验证码
     *
     * @param address
     * @return
     */
    public abstract Boolean sendValidCode(String address, String code);
}
