package com.lunarstra.quantum.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import com.lunarstra.quantum.service.base.BaseUserOpenKeyService;
import com.lunarstra.quantum.repository.UserOpenKeyRepository;

import org.springframework.stereotype.Service;

/**
 * 用户openKey 服务层实现。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
@Service
@Slf4j
public class UserOpenKeyService extends BaseUserOpenKeyService {
    @Resource
    private UserOpenKeyRepository userOpenKeyRepository;

}