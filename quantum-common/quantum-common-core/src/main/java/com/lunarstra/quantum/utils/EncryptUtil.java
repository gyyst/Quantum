package com.lunarstra.quantum.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;

import static com.lunarstra.quantum.constant.system.SystemConstant.SALT;

/**
 * @author gyyst
 * @Description
 * @Create by 2022/12/19 21:23
 */

public class EncryptUtil {

    private static final AES AES = SecureUtil.aes(SALT.getBytes());

    public static String encryptPassword(String password) {
        String md5Password = SecureUtil.md5(SALT + password);
        String encryptPassword = AES.encryptBase64(md5Password);
        return encryptPassword;
    }

    //    public static void main(String[] args) {
    //        //        String encryptBase64 = AES.encryptBase64("12345678");
    //        //        System.out.println(encryptBase64);
    //        //        String decryptStr = AES.decryptStr(encryptBase64);
    //        //        System.out.println(decryptStr);
    //        //        System.out.println(encryptUserPassword("12345678"));
    //        String encryptBySM4 = encryptPassword("12345678");
    //        System.out.println(encryptBySM4);
    //        //        System.out.println(decryptBySM4(encryptBySM4));
    //    }

    public static String encryptByAES(String context) {
        return AES.encryptBase64(context);
    }

    public static String decryptByAES(String context) {
        return AES.decryptStr(context);
    }
}
