package com.lunarstra.quantum.model.request;

import com.lunarstra.quantum.model.enums.RegisterEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author gyyst
 * @Description
 * @Create by 2025/10/20 00:07
 */
@Data
@Schema(defaultValue = "用户发送验证码类")
public class UserRegisterValidCodeSendRequest {

    @NotNull
    @Schema(description = "注册方式，email-邮箱")
    private RegisterEnum registerType;

    /**
     * 用户邮箱
     */
    @Email
    @Schema(description = "邮箱")
    private String email;
}
