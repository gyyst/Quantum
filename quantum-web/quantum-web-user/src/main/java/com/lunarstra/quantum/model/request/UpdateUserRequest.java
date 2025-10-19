package com.lunarstra.quantum.model.request;

import com.lunarstra.quantum.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户 修改请求类。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "接口")
public class UpdateUserRequest implements Serializable {

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    @NotBlank(message = "id不能为空")
    private String id;

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
    @Schema(description = "用户邮箱")
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
     * 用户状态
     */
    @Schema(description = "用户状态")
    private User.UserState state;

}