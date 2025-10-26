package com.lunarstra.quantum.model.response;

import cn.dev33.satoken.stp.SaTokenInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 已登录用户视图（脱敏）
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserResponse implements Serializable {

    private static final long serialVersionUID = 6037052489080981992L;

    /**
     * token信息
     */
    private SaTokenInfo tokenInfo;

    /**
     * id
     */
    private String id;

    /**
     * id
     */
    private String account;

    /**
     * 用户角色
     */
    private List<String> roles;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户昵称
     */
    private String name;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户简介
     */
    private String profile;

    /**
     * 用户状态
     */
    private String stateString;

}
