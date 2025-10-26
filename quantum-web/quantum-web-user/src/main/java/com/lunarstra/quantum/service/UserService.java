package com.lunarstra.quantum.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.lunarstra.quantum.common.ErrorCode;
import com.lunarstra.quantum.exception.BusinessException;
import com.lunarstra.quantum.exception.ThrowUtils;
import com.lunarstra.quantum.model.converter.UserConverter;
import com.lunarstra.quantum.model.entity.User;
import com.lunarstra.quantum.model.request.UserLoginRequest;
import com.lunarstra.quantum.model.request.UserRegisterRequest;
import com.lunarstra.quantum.model.request.UserRegisterValidCodeSendRequest;
import com.lunarstra.quantum.model.response.LoginUserResponse;
import com.lunarstra.quantum.model.validator.RegisterValidator;
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

    /**
     * 登出
     *
     * @return
     */
    public Boolean logout() {
        StpUtil.logout();
        return true;
    }

    /**
     * 是否登录
     *
     * @return
     */
    public Boolean isLogin() {
        ThrowUtils.throwIf(!StpUtil.isLogin(), ErrorCode.NOT_LOGIN_ERROR);
        return true;
    }

    /**
     * 用户注册
     *
     * @param registerRequest
     * @return
     */
    public LoginUserResponse register(UserRegisterRequest registerRequest) {
        //校验
        RegisterValidator.validRegisterInfo(registerRequest);

        boolean isExits = userRepository.checkAccountExist(registerRequest.getAccount());
        ThrowUtils.throwIf(isExits, ErrorCode.DATA_EXITS, "账号已被占用");

        User user = UserConverter.userRegisterRequestConvert2Entity(registerRequest);

        boolean save = userRepository.save(user);
        ThrowUtils.throwIf(!save, ErrorCode.OPERATION_ERROR, "注册用户失败，请联系管理员!");
        return UserConverter.convertUser2LoginUserInfo(user);
    }

    /**
     * 注册用户发送验证码
     *
     * @param request
     * @return
     */
    public Boolean validCodeSend(UserRegisterValidCodeSendRequest request) {
        return userRepository.validCodeSend(request);
    }
}