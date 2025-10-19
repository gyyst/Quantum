package com.lunarstra.quantum.model.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.EnumValue;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户 实体类。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户")
@Table("user")

public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = -1L;

    /**
     * 用户id
     */
    @Id(keyType = KeyType.Generator, value = "uuidv7")
    @Schema(description = "用户id")
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
    private UserState state;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @Column(isLogicDelete = true)
    @Schema(description = "是否删除")
    private Boolean isDelete;

    @Getter
    @AllArgsConstructor
    public enum UserState {
        /**
         * 正常
         */
        NORMAL(0, "正常"),
        /**
         * 禁用
         */
        BANNED(1, "禁用");

        @EnumValue
        private final int code;

        private final String description;
    }
}