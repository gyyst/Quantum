package com.lunarstra.quantum.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.lunarstra.quantum.annotation.RedisLimit;
import com.lunarstra.quantum.common.BaseResponse;
import com.lunarstra.quantum.constant.LimitType;
import com.lunarstra.quantum.model.request.UserLoginRequest;
import com.lunarstra.quantum.model.request.UserRegisterRequest;
import com.lunarstra.quantum.model.request.UserRegisterValidCodeSendRequest;
import com.lunarstra.quantum.model.response.LoginUserResponse;
import com.lunarstra.quantum.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户 控制层。
 *
 * @author lunarstra
 * @since 2025-10-18
 */
@Slf4j
@RestController
@Tag(name = "用户注册接口")
@RequestMapping("/login")
@RedisLimit(redisKeyPrefix = "UserController", limitType = LimitType.METHOD)
public class LoginController {

    @Resource
    private UserService userService;

    @PostMapping
    @RedisLimit(redisKeyPrefix = "LoginController")
    @Operation(summary = "用户登录", description = "用户登录接口，用于验证用户身份并登录系统")
    public BaseResponse<LoginUserResponse> userLogin(@RequestBody @Validated UserLoginRequest loginRequest) {
        return BaseResponse.success(userService.login(loginRequest));
    }

    @PostMapping("/register")
    @RedisLimit(redisKeyPrefix = "LoginController")
    @Operation(summary = "用户注册", description = "用户注册接口，用于创建新用户账户")
    public BaseResponse<LoginUserResponse> userRegister(@RequestBody @Validated UserRegisterRequest registerRequest) {
        return BaseResponse.success(userService.register(registerRequest));
    }

    @PostMapping("/valid/send")
    @RedisLimit(redisKeyPrefix = "LoginController")
    @Operation(summary = "发送验证码", description = "发送用户注册验证码到指定邮箱")
    public BaseResponse<Boolean> validCodeSend(@RequestBody @Validated UserRegisterValidCodeSendRequest request) {
        return BaseResponse.success(userService.validCodeSend(request));
    }

    @SaCheckLogin
    @GetMapping("/logout")
    @RedisLimit(redisKeyPrefix = "LoginController", limitType = LimitType.USER, count = 1)
    @Operation(summary = "用户登出", description = "用户登出接口，用于退出当前登录状态")
    public BaseResponse<Boolean> userLogout() {
        return BaseResponse.success(userService.logout());
    }

    @GetMapping("/isLogin")
    @RedisLimit(redisKeyPrefix = "LoginController", limitType = LimitType.USER, count = 1)
    @Operation(summary = "检查登录状态", description = "检查当前用户的登录状态")
    public BaseResponse<Boolean> isLogin() {
        return BaseResponse.success(userService.isLogin());
    }

}