package com.lunarstra.quantum.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 接口信息 修改请求类。
 *
 * @author lunarstra
 * @since 2025-10-12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "接口")
public class UpdateTestInfoRequest implements Serializable {

    /**
     * 主键
     */
    @Schema(description = "主键")
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private List<String> name;

}