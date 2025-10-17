package com.lunarstra.quantum.config;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @author gyyst
 * @Description 环境变量加密处理器
 * @Create by 2025/10/16 23:18
 */
@Configuration
public class EnvironmentEncryption implements EnvironmentPostProcessor {

    // 默认的AES密钥(16字节),仅作为后备方案
    private static final String DEFAULT_KEY = "DefaultKey123456";

    // 系统变量名称,用于获取AES密钥
    private static final String AES_KEY_ENV = "AES_ENCRYPTION_KEY";

    private AES aes;

    public static void main(String[] args) {
        String aesKey = new EnvironmentEncryption().getAesKey(new StandardEnvironment());
        AES aes1 = SecureUtil.aes(aesKey.getBytes(StandardCharsets.UTF_8));
        System.out.println(aes1.encryptHex("nacos.proxyz.dedyn.io:8848"));
        System.out.println(aes1.encryptBase64("nacos.proxyz.dedyn.io:8848"));
        System.out.println(aes1.decryptStr(aes1.encryptHex("nacos.proxyz.dedyn.io:8848")));
        System.out.println(aes1.decryptStr(aes1.encryptBase64("nacos.proxyz.dedyn.io:8848")));
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // 从系统变量或环境配置中获取盐值(密钥)
        String aesKey = getAesKey(environment);

        // 初始化AES加密器
        this.aes = SecureUtil.aes(aesKey.getBytes(StandardCharsets.UTF_8));

        Properties props = new Properties();  // 临时存储需要替换的配置

        // 假设加密密码前缀为 "ENC(",后缀为 ")"
        MutablePropertySources propertySources = environment.getPropertySources();
        for (PropertySource<?> propertySource : propertySources) {
            if (propertySource instanceof EnumerablePropertySource) {
                EnumerablePropertySource<?> enumerablePropertySource = (EnumerablePropertySource<?>) propertySource;
                String[] propertyNames = enumerablePropertySource.getPropertyNames();

                // 遍历所有配置key:value
                for (String propertyName : propertyNames) {
                    String propertyVal = environment.getProperty(propertyName);

                    // 根据自己写的规则来解析那些配置是需要解密的
                    if (propertyVal != null && propertyVal.startsWith("ENC(") && propertyVal.endsWith(")")) {
                        try {
                            // 解析得到加密的数据
                            String encryptedValue = propertyVal.substring(4, propertyVal.length() - 1);

                            // 调用自定义工具类解密
                            String decryptedValue = aes.decryptStr(encryptedValue);

                            // 保存需要替换的配置
                            props.put(propertyName, decryptedValue);
                        } catch (Exception e) {
                            throw new IllegalStateException("Failed to decrypt property: " + propertyName
                                + ". Please check if AES_ENCRYPTION_KEY is correctly configured.", e);
                        }
                    }
                }
            }
        }

        // 添加解密后的属性到环境中
        if (!props.isEmpty()) {
            PropertiesPropertySource pps = new PropertiesPropertySource("decryptedProperties", props);
            environment.getPropertySources().addFirst(pps);
        }
    }

    /**
     * 获取AES密钥
     * 优先级:
     * 1. 系统环境变量 (System.getenv)
     * 2. JVM系统属性 (System.getProperty)
     * 3. Spring配置文件中的配置
     * 4. 默认密钥(不推荐生产使用)
     */
    private String getAesKey(ConfigurableEnvironment environment) {
        // 1. 从系统环境变量获取
        String key = System.getenv(AES_KEY_ENV);
        if (key != null && !key.trim().isEmpty()) {
            return key;
        }

        // 2. 从JVM系统属性获取
        key = System.getProperty(AES_KEY_ENV);
        if (key != null && !key.trim().isEmpty()) {
            return key;
        }

        // 3. 从Spring配置中获取(未加密的配置)
        key = environment.getProperty("aes.encryption.key");
        if (key != null && !key.trim().isEmpty()) {
            return key;
        }

        // 4. 使用默认密钥(生产环境应该避免)
        System.err.println("WARNING: Using default AES encryption key. " + "Please set " + AES_KEY_ENV
            + " environment variable for production.");
        return DEFAULT_KEY;
    }
}