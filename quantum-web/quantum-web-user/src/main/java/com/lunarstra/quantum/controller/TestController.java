package com.lunarstra.quantum.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gyyst
 * @Description
 * @Create by 2025/9/30 22:01
 */
@RequestMapping("/test")
@RestController
public class TestController {
    @RequestMapping("/hello")
    public String hello() {
        System.out.println(Thread.currentThread());
        return "Hello World!";
    }
}
