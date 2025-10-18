package com.lunarstra.quantum.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户openKey 响应类。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "接口")
public class UserOpenKeyResponse implements Serializable {

    /**
     * 用户id
     */
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