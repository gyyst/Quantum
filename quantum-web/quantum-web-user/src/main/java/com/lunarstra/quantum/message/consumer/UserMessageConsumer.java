package com.lunarstra.quantum.message.consumer;

import cn.hutool.core.util.StrUtil;
import com.lunarstra.quantum.model.bo.UserRegisterSendCodeBO;
import com.lunarstra.quantum.strategy.validcode.AbstractValidCodeSender;
import com.lunarstra.quantum.strategy.validcode.ValidCodeSelector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.lunarstra.quantum.constant.RabbitExchangeConstant.USER_EXCHANGE;
import static com.lunarstra.quantum.constant.RabbitQueueConstant.USER_SEND_CODE;

/**
 * @author gyyst
 * @Description
 * @Create by 2025/10/29 22:35
 */
@Slf4j
@Component
public class UserMessageConsumer {

    /**
     * 接收验证码
     *
     * @param userRegisterSendCodeBO
     */
    @RabbitListener(
        bindings = @QueueBinding(value = @Queue(name = USER_SEND_CODE), exchange = @Exchange(name = USER_EXCHANGE),
            key = {USER_SEND_CODE}))
    public void sendEmail(UserRegisterSendCodeBO userRegisterSendCodeBO) {
        if (StrUtil.isEmpty(userRegisterSendCodeBO.getAddress()) || StrUtil.isEmpty(userRegisterSendCodeBO.getCode())) {
            log.error("地址{}:或验证码:{}为空", userRegisterSendCodeBO.getAddress(), userRegisterSendCodeBO.getCode());
            return;
        }
        // 获取发送的CodeSender
        AbstractValidCodeSender validCodeSender = ValidCodeSelector.select(userRegisterSendCodeBO.getRegisterEnum());

        validCodeSender.sendValidCode(userRegisterSendCodeBO.getAddress(), userRegisterSendCodeBO.getCode());

        log.info("发送到地址:{}，验证码:{}", userRegisterSendCodeBO.getAddress(), userRegisterSendCodeBO.getCode());
    }

}
