package com.lunarstra.quantum.config.dubbo;

import com.lunarstra.quantum.constant.system.SystemConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.slf4j.MDC;

/**
 * Sa-Token 整合 Dubbo Consumer端过滤器
 */
@Slf4j
@Activate(group = CommonConstants.CONSUMER)
public class DubboConsumerFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // 追加全链路日志调用
        log.info("DubboConsumerFilter:{}", MDC.get(SystemConstant.TRACE_ID));
        RpcContext.getClientAttachment().setAttachment(SystemConstant.TRACE_ID, MDC.get(SystemConstant.TRACE_ID));
        // 如果有其他自定义附加数据，如租户
        // RpcContext.getContext().setAttachment("tenantContext", tenantContext);
        // 开始调用
        return invoker.invoke(invocation);
    }
}
