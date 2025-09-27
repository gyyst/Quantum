package com.lunarstra.quantum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author gyyst
 * @Description
 * @Create by 2025/9/21 01:28
 */
@SpringBootApplication
@EnableDiscoveryClient
public class QuantumGateway5000 {
    public static void main(String[] args) {
        SpringApplication.run(QuantumGateway5000.class, args);
    }
}
