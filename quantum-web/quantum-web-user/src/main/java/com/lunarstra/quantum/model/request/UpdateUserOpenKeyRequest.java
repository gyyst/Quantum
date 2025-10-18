package com.lunarstra.quantum.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户openKey 修改请求类。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "接口")
public class UpdateUserOpenKeyRequest implements Serializable {

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    @NotBlank(message = "userId不能为空")
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

}