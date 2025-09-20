package com.lunarstra.quantum.common;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 删除请求
 */
@Data
public class DeleteRequest implements Serializable {

    private static final long serialVersionUID = 3527099168531914986L;

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id不能小于1")
    private Long id;
}
