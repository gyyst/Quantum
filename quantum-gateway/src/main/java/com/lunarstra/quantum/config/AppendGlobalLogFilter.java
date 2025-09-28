package com.lunarstra.quantum.config;

import cn.hutool.core.util.IdUtil;
import com.lunarstra.quantum.constant.system.SystemConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AppendGlobalLogFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request;
        String traceId = IdUtil.fastSimpleUUID();
        request = exchange.getRequest().mutate().header(SystemConstant.TRACE_ID, traceId).build();
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add(SystemConstant.TRACE_ID, traceId);
        exchange = exchange.mutate().request(request).response(response).build();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}