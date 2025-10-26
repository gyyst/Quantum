package com.lunarstra.quantum.strategy.validcode.impl;

import com.lunarstra.quantum.model.enums.RegisterEnum;
import com.lunarstra.quantum.strategy.validcode.AbstractValidCodeSender;
import com.lunarstra.quantum.utils.SendMailUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author gyyst
 * @Description
 * @Create by 2025/10/26 23:17
 */
@Component
public class EmailValidCodeSend extends AbstractValidCodeSender {

    @Resource
    private SendMailUtils sendMailUtils;

    /**
     * 获取注册方式
     *
     * @return
     */
    @Override
    public RegisterEnum getType() {
        return RegisterEnum.email;
    }

    /**
     * 发送验证码
     *
     * @param address
     * @param code
     * @return
     */
    @Override
    public Boolean sendValidCode(String address, String code) {
        sendMailUtils.sendMail(address, "验证码", code);
        return true;
    }
}
