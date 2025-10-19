package com.lunarstra.quantum.model.request;

import com.lunarstra.quantum.model.enums.RegisterEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author gyyst
 * @Description
 * @Create by 2025/10/19 22:25
 */
@Data
@Schema(defaultValue = "用户注册类")
public class UserRegisterRequest {

    @NotNull
    @Schema(description = "注册方式，email-邮箱")
    private RegisterEnum registerType;

    /**
     * 账号
     */
    @Schema(description = "账号")
    private String account;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 用户邮箱
     */
    @Email
    @Schema(description = "邮箱")
    private String email;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String name;

    /**
     * 用户头像
     */
    @Schema(description = "用户头像")
    private String avatar;

    /**
     * 用户简介
     */
    @Schema(description = "用户简介")
    private String profile;

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    private String code;

}
