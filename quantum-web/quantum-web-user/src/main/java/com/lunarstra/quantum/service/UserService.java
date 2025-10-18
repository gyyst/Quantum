package com.lunarstra.quantum.service;

import com.lunarstra.quantum.model.entity.User;
import com.lunarstra.quantum.model.request.UserLoginRequest;
import com.lunarstra.quantum.model.response.LoginUserResponse;
import com.lunarstra.quantum.repository.UserRepository;
import com.lunarstra.quantum.service.base.BaseUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户 服务层实现。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
@Service
@Slf4j
public class UserService extends BaseUserService {
    @Resource
    private UserRepository userRepository;

    /**
     * 用户登录
     *
     * @param loginRequest
     * @return
     */
    public LoginUserResponse login(UserLoginRequest loginRequest) {
        String account = loginRequest.getAccount();
        User user = userRepository.getUserByAccount(account);
    }
}