package com.lunarstra.quantum.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 用户角色关系 修改请求类。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "接口")
public class UpdateUserRoleRequest implements Serializable {

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    @NotBlank(message = "userId不能为空")
    private String userId;

    /**
     * 用户角色
     */
    @Schema(description = "用户角色")
    private List<String> roleList;

}