package com.lunarstra.quantum.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import com.lunarstra.quantum.service.base.BaseTestInfoService;
import com.lunarstra.quantum.repository.TestInfoRepository;

import org.springframework.stereotype.Service;

/**
 * 接口信息 服务层实现。
 *
 * @author lunarstra
 * @since 2025-10-12
 */
@Service
@Slf4j
public class TestInfoService extends BaseTestInfoService {
    @Resource
    private TestInfoRepository testInfoRepository;

}