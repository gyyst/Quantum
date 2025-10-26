package com.lunarstra.quantum.utils;

import com.lunarstra.quantum.constant.EmailTemplate;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * 发送模板邮件
     *
     * @param to 收件人邮箱
     * @param template 邮件模板
     * @param params 模板参数，用于替换模板中的变量
     */
    public void sendTemplateMail(String to, EmailTemplate template, Map<String, Object> params) {
        String subject = template.getTemplateName();
        String content = replaceTemplateVariables(template.getTemplateContent(), params);
        
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // 设置为HTML格式
            
            javaMailSender.send(message);
            log.info("发送模板邮件向{},模板为{}", to, template.getTemplateName());
        } catch (MessagingException e) {
            log.error("发送模板邮件失败: {}", e.getMessage(), e);
            throw new RuntimeException("发送模板邮件失败", e);
        }
    }

    /**
     * 发送HTML邮件
     *
     * @param to 收件人邮箱
     * @param subject 邮件主题
     * @param htmlContent HTML内容
     */
    public void sendHtmlMail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // 设置为HTML格式
            
            javaMailSender.send(message);
            log.info("发送HTML邮件向{},主题为{}", to, subject);
        } catch (MessagingException e) {
            log.error("发送HTML邮件失败: {}", e.getMessage(), e);
            throw new RuntimeException("发送HTML邮件失败", e);
        }
    }

    /**
     * 替换模板中的变量
     *
     * @param template 模板内容
     * @param params 参数Map
     * @return 替换后的内容
     */
    private String replaceTemplateVariables(String template, Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return template;
        }
        
        String result = template;
        Pattern pattern = Pattern.compile("\\{\\{(\\w+)\\}\\}");
        Matcher matcher = pattern.matcher(result);
        
        while (matcher.find()) {
            String key = matcher.group(1);
            Object value = params.get(key);
            if (value != null) {
                result = result.replace(matcher.group(), value.toString());
            }
        }
        
        return result;
    }

}
