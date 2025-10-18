package com.lunarstra.quantum.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import com.lunarstra.quantum.service.base.BaseUserRoleService;
import com.lunarstra.quantum.repository.UserRoleRepository;

import org.springframework.stereotype.Service;

/**
 * 用户角色关系 服务层实现。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
@Service
@Slf4j
public class UserRoleService extends BaseUserRoleService {
    @Resource
    private UserRoleRepository userRoleRepository;

}