package com.lunarstra.quantum;

import love.forte.simbot.spring.EnableSimbot;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 主类（项目启动入口）
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableSimbot
@EnableDubbo
public class QuantumBotService5009 {
    public static void main(String[] args) {
        SpringApplication.run(QuantumBotService5009.class, args);
    }

}