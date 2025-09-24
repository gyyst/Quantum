package com.lunarstra.quantum.config.dubbo;

import cn.dev33.satoken.same.SaSameUtil;
import com.lunarstra.quantum.constant.system.SystemConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.slf4j.MDC;

/**
 * Sa-Token 整合 Dubbo Provider端过滤器
 */
@Slf4j
@Activate(group = CommonConstants.PROVIDER)
public class DubboProviderFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        //        SaSameUtil.checkToken(sameToken);

        //增加分布式日志链路追踪
        String traceId = invocation.getAttachment(SystemConstant.TRACE_ID);
        MDC.put(SystemConstant.TRACE_ID, traceId);
        log.info("DubboProviderFilter:{}", traceId);
        // 取出其他自定义附加数据
        // TenantContext tenantContext = invocation.getAttachment("tenantContext");
        // 开始调用
        Result invoke;
        try {
            invoke = invoker.invoke(invocation);
        } finally {
            MDC.remove(SystemConstant.TRACE_ID);
        }
        return invoke;
    }

}
