package com.lunarstra.quantum.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.lunarstra.quantum.common.ErrorCode;
import com.lunarstra.quantum.exception.BusinessException;
import com.lunarstra.quantum.model.converter.UserConverter;
import com.lunarstra.quantum.model.entity.User;
import com.lunarstra.quantum.model.request.UserLoginRequest;
import com.lunarstra.quantum.model.response.LoginUserResponse;
import com.lunarstra.quantum.repository.UserRepository;
import com.lunarstra.quantum.service.base.BaseUserService;
import com.lunarstra.quantum.utils.EncryptUtil;
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
        String userPassword = user.getPassword();
        String encryptPassword = EncryptUtil.encryptPassword(loginRequest.getPassword());
        if (!StrUtil.equals(encryptPassword, userPassword, true)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        StpUtil.login(user.getId(), loginRequest.getRemember());
        return UserConverter.convertUser2LoginUserInfo(user);
    }
}