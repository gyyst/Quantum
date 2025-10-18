package com.lunarstra.quantum.model.entity;

import java.io.Serial;
import com.mybatisflex.core.handler.JacksonTypeHandler;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 用户openKey 实体类。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户openKey")
@Table("user_open_key")

public class UserOpenKey implements Serializable {

    @Serial
    private static final long serialVersionUID = -1L;

    /**
     * 用户id
     */
    @Id(keyType = KeyType.Generator, value = "uuidv7")
    @Schema(description = "用户id")
    private String userId;

    /**
     * access_key
     */
    @Schema(description = "access_key")
    private String accessKey;

    /**
     * secret_key，存储sha256加密后的字符串
     */
    @Schema(description = "secret_key，存储sha256加密后的字符串")
    private String secretKey;

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

}