package com.lunarstra.quantum.constant;

/**
 * @author gyyst
 * @Description 邮件模板枚举类
 * @Create by 2025/10/22 22:33
 */
public enum EmailTemplate {
    
    /**
     * 用户注册验证邮件模板
     */
    REGISTER_VERIFICATION("register_verification", "用户注册验证", 
            """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>用户注册验证</title>
            </head>
            <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                <div style="max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 5px;">
                    <h2 style="color: #2c3e50; text-align: center;">欢迎使用我们的服务</h2>
                    <p>您好，{username}！</p>
                    <p>感谢您注册我们的服务。请点击下面的链接完成邮箱验证：</p>
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="{verificationUrl}" style="background-color: #3498db; color: white; padding: 12px 25px; text-decoration: none; border-radius: 4px; display: inline-block;">验证邮箱</a>
                    </div>
                    <p>如果按钮无法点击，请复制以下链接到浏览器地址栏：</p>
                    <p style="word-break: break-all; background-color: #f8f9fa; padding: 10px; border-radius: 4px;">{verificationUrl}</p>
                    <p>此链接将在24小时后失效。</p>
                    <hr style="margin: 30px 0; border: none; border-top: 1px solid #eee;">
                    <p style="font-size: 12px; color: #7f8c8d;">如果您没有注册我们的服务，请忽略此邮件。</p>
                </div>
            </body>
            </html>
            """),
    
    /**
     * 密码重置邮件模板
     */
    PASSWORD_RESET("password_reset", "密码重置", 
            """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>密码重置</title>
            </head>
            <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                <div style="max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 5px;">
                    <h2 style="color: #e74c3c; text-align: center;">密码重置请求</h2>
                    <p>您好，{username}！</p>
                    <p>我们收到了您的密码重置请求。请点击下面的链接重置您的密码：</p>
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="{resetUrl}" style="background-color: #e74c3c; color: white; padding: 12px 25px; text-decoration: none; border-radius: 4px; display: inline-block;">重置密码</a>
                    </div>
                    <p>如果按钮无法点击，请复制以下链接到浏览器地址栏：</p>
                    <p style="word-break: break-all; background-color: #f8f9fa; padding: 10px; border-radius: 4px;">{resetUrl}</p>
                    <p>此链接将在1小时后失效。</p>
                    <hr style="margin: 30px 0; border: none; border-top: 1px solid #eee;">
                    <p style="font-size: 12px; color: #7f8c8d;">如果您没有请求重置密码，请忽略此邮件或联系我们的客服。</p>
                </div>
            </body>
            </html>
            """),
    
    /**
     * 账户通知邮件模板
     */
    ACCOUNT_NOTIFICATION("account_notification", "账户通知", 
            """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>账户通知</title>
            </head>
            <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                <div style="max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 5px;">
                    <h2 style="color: #2c3e50; text-align: center;">账户通知</h2>
                    <p>您好，{username}！</p>
                    <p>{notificationContent}</p>
                    <div style="margin: 30px 0; padding: 15px; background-color: #f8f9fa; border-left: 4px solid #3498db;">
                        <p style="margin: 0;"><strong>通知详情：</strong></p>
                        <p style="margin: 5px 0;">{notificationDetails}</p>
                    </div>
                    <p>如有任何疑问，请随时联系我们的客服团队。</p>
                    <hr style="margin: 30px 0; border: none; border-top: 1px solid #eee;">
                    <p style="font-size: 12px; color: #7f8c8d;">此邮件由系统自动发送，请勿回复。</p>
                </div>
            </body>
            </html>
            """),
    
    /**
     * 订单确认邮件模板
     */
    ORDER_CONFIRMATION("order_confirmation", "订单确认", 
            """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>订单确认</title>
            </head>
            <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                <div style="max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 5px;">
                    <h2 style="color: #27ae60; text-align: center;">订单确认</h2>
                    <p>您好，{username}！</p>
                    <p>感谢您的购买！您的订单已确认，详情如下：</p>
                    <div style="margin: 20px 0; padding: 15px; background-color: #f8f9fa; border-radius: 4px;">
                        <p><strong>订单号：</strong>{orderNumber}</p>
                        <p><strong>下单时间：</strong>{orderTime}</p>
                        <p><strong>订单总额：</strong>{orderAmount}</p>
                        <p><strong>支付方式：</strong>{paymentMethod}</p>
                    </div>
                    <div style="margin: 20px 0;">
                        <h3>订单详情：</h3>
                        {orderDetails}
                    </div>
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="{orderUrl}" style="background-color: #27ae60; color: white; padding: 12px 25px; text-decoration: none; border-radius: 4px; display: inline-block;">查看订单</a>
                    </div>
                    <hr style="margin: 30px 0; border: none; border-top: 1px solid #eee;">
                    <p style="font-size: 12px; color: #7f8c8d;">如有任何问题，请联系我们的客服团队。</p>
                </div>
            </body>
            </html>
            """),
    
    /**
     * 营销推广邮件模板
     */
    MARKETING_PROMOTION("marketing_promotion", "优惠活动", 
            """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>优惠活动</title>
            </head>
            <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                <div style="max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 5px;">
                    <h2 style="color: #e67e22; text-align: center;">{promotionTitle}</h2>
                    <p>您好，{username}！</p>
                    <p>{promotionDescription}</p>
                    <div style="margin: 20px 0; padding: 15px; background-color: #fff3cd; border: 1px solid #ffeaa7; border-radius: 4px;">
                        <p style="margin: 0; text-align: center; font-size: 18px; color: #d63031;"><strong>优惠码：{promotionCode}</strong></p>
                        <p style="margin: 5px 0; text-align: center;">有效期至：{expiryDate}</p>
                    </div>
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="{promotionUrl}" style="background-color: #e67e22; color: white; padding: 12px 25px; text-decoration: none; border-radius: 4px; display: inline-block;">立即查看</a>
                    </div>
                    <hr style="margin: 30px 0; border: none; border-top: 1px solid #eee;">
                    <p style="font-size: 12px; color: #7f8c8d;">如果您不想接收此类邮件，请点击<a href="{unsubscribeUrl}" style="color: #7f8c8d;">取消订阅</a>。</p>
                </div>
            </body>
            </html>
            """),
    
    /**
     * 系统维护通知邮件模板
     */
    SYSTEM_MAINTENANCE("system_maintenance", "系统维护通知", 
            """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>系统维护通知</title>
            </head>
            <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                <div style="max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 5px;">
                    <h2 style="color: #34495e; text-align: center;">系统维护通知</h2>
                    <p>尊敬的用户：</p>
                    <p>为了提供更好的服务，我们将进行系统维护升级。</p>
                    <div style="margin: 20px 0; padding: 15px; background-color: #f8f9fa; border-left: 4px solid #3498db;">
                        <p><strong>维护时间：</strong>{maintenanceStartTime} 至 {maintenanceEndTime}</p>
                        <p><strong>影响范围：</strong>{affectedServices}</p>
                        <p><strong>维护内容：</strong>{maintenanceContent}</p>
                    </div>
                    <p>在维护期间，相关服务可能会暂时无法使用。给您带来的不便，敬请谅解。</p>
                    <p>如有任何疑问，请随时联系我们的客服团队。</p>
                    <hr style="margin: 30px 0; border: none; border-top: 1px solid #eee;">
                    <p style="font-size: 12px; color: #7f8c8d;">此邮件由系统自动发送，请勿回复。</p>
                </div>
            </body>
            </html>
            """);
    
    private final String templateCode;
    private final String templateName;
    private final String templateContent;
    
    EmailTemplate(String templateCode, String templateName, String templateContent) {
        this.templateCode = templateCode;
        this.templateName = templateName;
        this.templateContent = templateContent;
    }
    
    public String getTemplateCode() {
        return templateCode;
    }
    
    public String getTemplateName() {
        return templateName;
    }
    
    public String getTemplateContent() {
        return templateContent;
    }
}
