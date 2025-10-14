package com.lunarstra.quantum.model.entity;

import java.io.Serial;
import com.mybatisflex.core.handler.JacksonTypeHandler;
import com.mybatisflex.annotation.Column;
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
import java.util.List;

/**
 * 接口信息 实体类。
 *
 * @author lunarstra
 * @since 2025-10-12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "接口信息")
@Table("test_info")

public class TestInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = -1L;

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto)
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

    /**
     * 是否删除
     */
    @Column(isLogicDelete = true)
    @Schema(description = "是否删除")
    private Boolean isDelete;

}