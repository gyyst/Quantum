package com.lunarstra.quantum.model.validator;

import com.lunarstra.quantum.common.ErrorCode;
import com.lunarstra.quantum.exception.ThrowUtils;
import com.lunarstra.quantum.model.request.UserRegisterRequest;

/**
 * @author gyyst
 * @Description
 * @Create by 2025/10/19 22:43
 */

public class RegisterValidator {

    public static void validRegisterInfo(UserRegisterRequest registerRequest) {
        switch (registerRequest.getRegisterType()) {
            case email -> {
                ThrowUtils.throwIf(registerRequest.getEmail() == null, ErrorCode.PARAMS_ERROR, "邮箱不能为空");
            }
            default -> throw new IllegalStateException("Unexpected value: " + registerRequest.getRegisterType());
        }
    }
}
