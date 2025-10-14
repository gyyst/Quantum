package com.lunarstra.quantum.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 接口信息 响应类。
 *
 * @author lunarstra
 * @since 2025-10-12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "接口")
public class TestInfoResponse implements Serializable {

    /**
     * 主键
     */
    @Schema(description = "主键")
    private Long id;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private List<String> name;

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