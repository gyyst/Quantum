package com.lunarstra.quantum.config;

import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局跨域配置
 *
 * @author gyyst
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Resource
    private LogInterceptor logInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 覆盖所有请求
        registry.addMapping("/**")
            // 允许发送 Cookie
            .allowCredentials(true)
            // 放行哪些域名（必须用 patterns，否则 * 会和 allowCredentials 冲突）
            .allowedOriginPatterns("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .exposedHeaders("*");
    }

    // 注册 Sa-Token 全局过滤器
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter();
        //                .addInclude("/**")
        //                .addExclude("/favicon.ico", "/doc.html", "/v3/api-docs")
        //                .setAuth(obj -> {
        //                    // 校验 Same-Token 身份凭证     —— 以下两句代码可简化为：SaSameUtil.checkCurrentRequestToken();
        //                    //                        String token = SaHolder.getRequest().getHeader(SaSameUtil.SAME_TOKEN);
        //                    //                        SaSameUtil.checkToken(token);
        //                    SaRouter.match("/**", "/m/**", SaSameUtil::checkCurrentRequestToken);
        ////                    SaRouter.match("/m/**", () -> {
        ////                        String token = SaHolder.getRequest().getHeader(SaSameUtil.SAME_TOKEN);
        ////                        SaSameUtil.checkToken(token);
        ////                    });
        //
        //                })
        //                .setError(e -> BaseResponse.error(ErrorCode.NO_AUTH_ERROR, "请通过网关访问"));

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 可添加多个
        registry.addInterceptor(new SaInterceptor())
            .addPathPatterns("/**")
            .excludePathPatterns("/doc.html", "/v3/api-docs", "");
        registry.addInterceptor(logInterceptor).addPathPatterns("/**").excludePathPatterns("/doc.html", "/v3/api-docs");
    }

}