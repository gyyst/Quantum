package com.lunarstra.quantum.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.lunarstra.quantum.common.BaseResponse;
import com.lunarstra.quantum.common.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局异常处理器
 *
 * @author gyyst
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("businessException: " + e.getMessage());
        return BaseResponse.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(NotRoleException.class)
    public BaseResponse<?> notRoleExceptionHandler(NotRoleException e) {
        log.error("notRoleException: " + e.getMessage());
        return BaseResponse.error(ErrorCode.NO_AUTH_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public BaseResponse<?> IllegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error("IllegalArgumentException: " + e.getMessage());
        return BaseResponse.error(ErrorCode.PARAMS_ERROR, e.getMessage());
    }

    @ExceptionHandler(NotPermissionException.class)
    public BaseResponse<?> NotPermissionExceptionHandler(NotPermissionException e) {
        log.error("notPermissionException: " + e.getMessage());
        return BaseResponse.error(ErrorCode.NO_AUTH_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> RuntimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException: " + e.getMessage());
        return BaseResponse.error(ErrorCode.OPERATION_ERROR, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse<?> ExceptionHandler(Exception e) {
        log.error("businessException: " + e.getMessage());
        return BaseResponse.error(ErrorCode.SYSTEM_ERROR);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public BaseResponse<?> MethodArgumentNotValidExceptionHandler(
        MethodArgumentNotValidException argumentNotValidException) {
        BindingResult bindingResult = argumentNotValidException.getBindingResult();
        StringBuilder errMsg = new StringBuilder();

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        fieldErrors.forEach(error -> {
            errMsg.append(error.getDefaultMessage()).append(",");
        });
        log.error(errMsg.toString());
        return BaseResponse.error(ErrorCode.PARAMS_ERROR, errMsg.toString());
    }

    // 全局异常拦截（拦截项目中的NotLoginException异常）
    @ExceptionHandler(NotLoginException.class)
    public BaseResponse<?> NotLoginExceptionHandler(NotLoginException nle) {
        // 判断场景值，定制化异常信息
        String message = "";
        if (nle.getType().equals(NotLoginException.NOT_TOKEN)) {
            message = "未提供token";
        } else if (nle.getType().equals(NotLoginException.INVALID_TOKEN)) {
            message = "token无效";
        } else if (nle.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            message = "token已过期";
        } else if (nle.getType().equals(NotLoginException.BE_REPLACED)) {
            message = "token已被顶下线";
        } else if (nle.getType().equals(NotLoginException.KICK_OUT)) {
            message = "token已被踢下线";
        } else {
            message = "当前会话未登录";
        }
        log.error("notLoginException: " + nle.getMessage());
        // 返回给前端
        return BaseResponse.error(ErrorCode.NOT_LOGIN_ERROR, message);
    }

}
