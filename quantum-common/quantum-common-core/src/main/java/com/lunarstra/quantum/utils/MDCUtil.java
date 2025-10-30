package com.lunarstra.quantum.utils;

import com.lunarstra.quantum.constant.system.SystemConstant;
import org.slf4j.MDC;

/**
 * @author gyyst
 * @Description
 * @Create by 2025/10/29 23:46
 */

public class MDCUtil {

    public static void putMDC(String traceId, String spanId) {
        MDC.put(SystemConstant.TRACE_ID, traceId);
        MDC.put(SystemConstant.SPAN_ID, spanId);
    }

    public static void clearMDC() {
        MDC.remove(SystemConstant.TRACE_ID);
        MDC.remove(SystemConstant.SPAN_ID);
    }
}
