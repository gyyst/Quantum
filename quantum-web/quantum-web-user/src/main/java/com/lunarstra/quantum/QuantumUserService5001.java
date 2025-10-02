package com.lunarstra.quantum;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 主类（项目启动入口）
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo
public class QuantumUserService5001 {
    public static void main(String[] args) {
        SpringApplication.run(QuantumUserService5001.class, args);
    }

}