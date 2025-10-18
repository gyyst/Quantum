package com.lunarstra.quantum.config;

import com.lunarstra.quantum.utils.EncryptUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.Properties;

/**
 * @author gyyst
 * @Description 环境变量加密处理器
 * @Create by 2025/10/16 23:18
 */
@Configuration
public class EnvironmentEncryption implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // 从系统变量或环境配置中获取盐值(密钥)

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
                    if (propertyVal != null && EncryptUtil.isEncryptStr(propertyVal)) {
                        try {
                            // 解析得到加密的数据
                            String encryptedValue = propertyVal.substring(4, propertyVal.length() - 1);

                            // 调用自定义工具类解密
                            String decryptedValue = EncryptUtil.decryptByAES(encryptedValue);

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

}