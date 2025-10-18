package com.lunarstra.quantum.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import com.lunarstra.quantum.service.base.BaseUserCoinService;
import com.lunarstra.quantum.repository.UserCoinRepository;

import org.springframework.stereotype.Service;

/**
 * 用户硬币 服务层实现。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
@Service
@Slf4j
public class UserCoinService extends BaseUserCoinService {
    @Resource
    private UserCoinRepository userCoinRepository;

}