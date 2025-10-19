package com.lunarstra.quantum.controller;

import com.lunarstra.quantum.annotation.RedisLimit;
import com.lunarstra.quantum.common.BaseResponse;
import com.lunarstra.quantum.constant.LimitType;
import com.lunarstra.quantum.model.request.UserLoginRequest;
import com.lunarstra.quantum.model.response.LoginUserResponse;
import com.lunarstra.quantum.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
@Tag(name = "用户接口")
@RequestMapping("/login")
@RedisLimit(redisKeyPrefix = "UserController", limitType = LimitType.METHOD)
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping
    @RedisLimit(redisKeyPrefix = "LoginController")
    public BaseResponse<LoginUserResponse> userLogin(@RequestBody @Validated UserLoginRequest loginRequest) {
        return BaseResponse.success(userService.login(loginRequest));
    }
    //
    //    @SaCheckLogin
    //    @GetMapping("/logout")
    //    @RedisLimit(redisKeyPrefix = "LoginController", limitType = LimitType.USER, count = 1)
    //    public BaseResponse<Boolean> userLogout() {
    //        return BaseResponse.success(userService.logout());
    //    }
    //
    //    @GetMapping("/isLogin")
    //    @RedisLimit(redisKeyPrefix = "LoginController", limitType = LimitType.USER, count = 1)
    //    public BaseResponse<Boolean> isLogin() {
    //        return BaseResponse.success(userService.isLogin());
    //    }

}