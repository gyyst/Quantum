package com.lunarstra.quantum.common;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.io.Serializable;

/**
 * @author gyyst
 * @Description
 * @Create by 2023/4/19 14:07
 */

@Data
public class PageRequest implements Serializable {
    @Min(value = 1, message = "当前页应该>=1")
    protected Long current = 1L;

    @Min(value = 1, message = "每页数量应大于0")
    @Max(value = 100, message = "每页数量不能超过100")
    protected Long pageSize = 10L;
}
