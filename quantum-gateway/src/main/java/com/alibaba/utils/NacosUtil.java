package com.alibaba.utils;

import com.lunarstra.quantum.utils.EncryptUtil;

import java.util.Properties;

/**
 * @author gyyst
 * @Description
 * @Create by 2025/10/17 22:31
 */

public class NacosUtil {
    public static Properties filterEncrypt(Properties properties) {
        properties.forEach((key, value1) -> {
            if (value1 instanceof String s) {
                if (EncryptUtil.isEncryptStr(s)) {
                    properties.setProperty((String) key, EncryptUtil.decryptByAES(EncryptUtil.trimENC(s)));
                }
            }
        });
        return properties;
    }
}
