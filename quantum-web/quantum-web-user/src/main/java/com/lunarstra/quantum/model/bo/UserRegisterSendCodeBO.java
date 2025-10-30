package com.lunarstra.quantum.model.bo;

import com.lunarstra.quantum.model.enums.RegisterEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author gyyst
 * @Description
 * @Create by 2022/12/18 19:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterSendCodeBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 7193110316996734520L;

    private RegisterEnum registerEnum;

    private String address;

    private String code;
}
