package com.lunarstra.quantum.config;

import cn.hutool.core.util.IdUtil;
import com.lunarstra.quantum.constant.system.SystemConstant;
import com.lunarstra.quantum.utils.MDCUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        //如果有上层调用就用上层的ID
        String traceId = request.getHeader(SystemConstant.TRACE_ID);
        if (traceId == null) {
            traceId = IdUtil.fastSimpleUUID();
        }
        MDCUtil.putMDC(traceId, IdUtil.fastSimpleUUID());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {

        MDCUtil.clearMDC();
    }
}
