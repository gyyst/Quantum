package com.lunarstra.quantum.utils;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Lazy
public class SendMailUtils {
    @Value("${spring.mail.username:123@123.com}")
    private String from;

    @Resource
    private JavaMailSender javaMailSender;

    public void sendMail(String to, String subject, String context) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(context);
        javaMailSender.send(message);
        log.info("发送邮件向{},主题为{},内容为{}", to, subject, context);
    }

}
