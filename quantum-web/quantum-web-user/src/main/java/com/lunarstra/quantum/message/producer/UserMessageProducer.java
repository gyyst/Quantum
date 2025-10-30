package com.lunarstra.quantum.message.producer;

import com.lunarstra.quantum.model.bo.UserRegisterSendCodeBO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
public class UserMessageProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 邮箱发送注册验证码
     *
     * @param userRegisterSendCodeBO
     * @return
     */
    public boolean sendvalidCode(UserRegisterSendCodeBO userRegisterSendCodeBO) {
        try {
            rabbitTemplate.convertAndSend(USER_EXCHANGE, USER_SEND_CODE, userRegisterSendCodeBO);
            log.info("用户{}注册验证码发送到mq:{}", userRegisterSendCodeBO.getAddress(),
                userRegisterSendCodeBO.getCode());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
