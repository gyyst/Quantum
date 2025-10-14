package com.lunarstra.quantum.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class StaticResourceFilter implements GlobalFilter, Ordered {

    private static final List<String> IGNORE_PATHS = Arrays.asList(".well-known", "favicon.ico", ".map",
        "chrome-devtools");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        boolean shouldIgnore = IGNORE_PATHS.stream().anyMatch(path::contains);

        if (shouldIgnore) {
            exchange.getResponse().setStatusCode(HttpStatus.NO_CONTENT);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}