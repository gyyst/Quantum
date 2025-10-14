package com.lunarstra.quantum.config;

import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

/**
 * [Sa-Token 权限认证] 配置类
 *
 * @author kong
 */
//@Configuration
@Slf4j
public class SaTokenConfigure {
    // 注册 Sa-Token全局过滤器
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
            // 拦截地址
            .addInclude("/**")    /* 拦截全部path */
            // 开放地址
            .addExclude(".well-known/appspecific/com.chrome.devtools.json", "/favicon.ico", "/doc.html",
                "/**/v3/api-docs", "/swagger-ui/index.html", "/v3/api-docs", "/v3/api-docs/swagger-config",
                "/v3/api-docs/**")
            // 鉴权方法：每次访问进入
            .setAuth(obj -> {
                //                     登录校验 -- 拦截所有路由，并排除/user/doLogin 用于开放登录
                //                    SaRouter.match("/**", "/**/user/login", r -> StpUtil.checkLogin());
                //                     权限认证 -- 不同模块, 校验不同权限
                //                    SaRouter.match("/user/**", r -> StpUtil.checkPermission("user"));
                //                    SaRouter.match("/admin/**", r -> StpUtil.checkPermission("admin"));
                //                    SaRouter.match("/goods/**", r -> StpUtil.checkPermission("goods"));
                //                    SaRouter.match("/orders/**", r -> StpUtil.checkPermission("orders"));
                log.info("{}", obj);
                //                     更多匹配 ...  */
            })
            // 异常处理方法：每次setAuth函数出现异常时进入
            .setError(e -> {
                log.error("{}", e.getMessage());
                return SaResult.error(e.getMessage());
            });
    }
}
