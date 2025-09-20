package com.lunarstra.quantum.circle;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author gyyst
 * @Description
 * @Create by 2024/7/3 上午12:35
 */
@Component
@Slf4j
public class PingMq {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0 0 * * * *")
    public void pingMq() {
        rabbitTemplate.convertAndSend("ping", "ping", "ping");
    }

    @RabbitListener(
        bindings = @QueueBinding(value = @Queue(name = "ping"), exchange = @Exchange(name = "ping"), key = {"ping"}))
    public void sendEmail(String ping) {
        log.debug("{} rabbitmq", ping);
    }
}