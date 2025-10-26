package com.lunarstra.quantum.strategy.validcode;

import com.lunarstra.quantum.model.enums.RegisterEnum;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gyyst
 * @Description
 * @Create by 2025/10/26 21:09
 */
@Component
public class ValidCodeSelector implements ApplicationContextAware {
    private static final Map<RegisterEnum, AbstractValidCodeSender> register = HashMap.newHashMap(1);

    /**
     * 获取发送者
     *
     * @param type
     * @return
     */
    public static AbstractValidCodeSender select(RegisterEnum type) {
        return register.get(type);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, AbstractValidCodeSender> beans = applicationContext.getBeansOfType(AbstractValidCodeSender.class);
        beans.forEach((k, v) -> {register.put(v.getType(), v);});
    }
}
