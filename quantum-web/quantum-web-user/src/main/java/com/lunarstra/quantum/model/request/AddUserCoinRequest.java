package com.lunarstra.quantum.model.request;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户硬币 新增请求类。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "接口")
public class AddUserCoinRequest implements Serializable {

    /**
     * 用户id
     */
    @Id(keyType = KeyType.Generator, value = "uuidv7")
    @Schema(description = "用户id")
    @NotBlank(message = "userId不能为空")
    private String userId;

    /**
     * 硬币数量
     */
    @Schema(description = "硬币数量")
    @NotNull(message = "coinNum不能为空")
    private BigDecimal coinNum;

}