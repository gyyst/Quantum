package com.lunarstra.quantum.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.env.ConfigurableEnvironment;

import static com.lunarstra.quantum.constant.system.SystemConstant.AES_ENV_KEY;
import static com.lunarstra.quantum.constant.system.SystemConstant.SALT;

/**
 * @author gyyst
 * @Description
 * @Create by 2022/12/19 21:23
 */

public class EncryptUtil {

    private static final AES AES = SecureUtil.aes(getAesKey().getBytes());

    public static String encryptPassword(String password) {
        String md5Password = SecureUtil.md5(SALT + password);
        String encryptPassword = AES.encryptHex(md5Password);
        return encryptPassword;
    }

    public static String encryptByAES(String context) {
        return AES.encryptHex(context);
    }

    /**
     * 如果是密文ENC(*)则先取出密文
     *
     * @param context
     * @return
     */
    public static String decryptByAES(String context) {
        if (isEncryptStr(context)) {
            context = trimENC(context);
        }
        return AES.decryptStr(context);
    }

    /**
     * 获取AES密钥
     * 优先级:
     * 1. 系统环境变量 (System.getenv)
     * 2. JVM系统属性 (System.getProperty)
     * 3. Spring配置文件中的配置
     * 4. 默认密钥(不推荐生产使用)
     */
    private static String getAesKey(ConfigurableEnvironment environment) {
        // 1. 从系统环境变量获取
        String key = System.getenv(AES_ENV_KEY);
        if (key != null && !key.trim().isEmpty()) {
            return key;
        }

        // 2. 从JVM系统属性获取
        key = System.getProperty(AES_ENV_KEY);
        if (key != null && !key.trim().isEmpty()) {
            return key;
        }

        // 3. 从Spring配置中获取(未加密的配置)
        key = environment.getProperty("aes.encryption.key");
        if (key != null && !key.trim().isEmpty()) {
            return key;
        }

        // 4. 使用默认密钥(生产环境应该避免)
        System.err.println("WARNING: Using default AES encryption key. " + "Please set " + AES_ENV_KEY
            + " environment variable for production.");
        return SALT;
    }

    /**
     * 获取AES密钥
     * 优先级:
     * 1. 系统环境变量 (System.getenv)
     * 2. JVM系统属性 (System.getProperty)
     * 3. Spring配置文件中的配置
     * 4. 默认密钥(不推荐生产使用)
     */
    private static String getAesKey() {
        // 1. 从系统环境变量获取
        String key = System.getenv(AES_ENV_KEY);
        if (key != null && !key.trim().isEmpty()) {
            return key;
        }

        // 2. 从JVM系统属性获取
        key = System.getProperty(AES_ENV_KEY);
        if (key != null && !key.trim().isEmpty()) {
            return key;
        }

        // 4. 使用默认密钥(生产环境应该避免)
        System.err.println("WARNING: Using default AES encryption key. " + "Please set " + AES_ENV_KEY
            + " environment variable for production.");
        return SALT;
    }

    /**
     * 判断是否非加密
     *
     * @param context
     * @return
     */
    public static boolean isEncryptStr(String context) {
        if (Strings.isBlank(context)) {
            return false;
        }
        String trim = context.trim();
        return trim.startsWith("ENC(") && trim.endsWith(")");
    }

    public static String trimENC(String context) {
        if (!isEncryptStr(context)) {
            throw new UnsupportedOperationException("非密文格式ENC(*)");
        }
        return context.substring(4, context.length() - 1);
    }
}
