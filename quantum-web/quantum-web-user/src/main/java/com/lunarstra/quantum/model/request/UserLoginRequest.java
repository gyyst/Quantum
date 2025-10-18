package com.lunarstra.quantum.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户
 *
 * @TableName user
 */
@Data
@Schema(defaultValue = "用户登录请求类")
public class UserLoginRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 367407857106184931L;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Length(min = 8, message = "密码过短")
    @SchemaProperty(name = "密码")
    private String password;

    /**
     * 用户邮箱
     */
    @SchemaProperty(name = "账户")
    @NotBlank(message = "账户不能为空")
    private String account;

    @SchemaProperty(name = "记住我")
    private Boolean remember = false;

    private String code;
}
