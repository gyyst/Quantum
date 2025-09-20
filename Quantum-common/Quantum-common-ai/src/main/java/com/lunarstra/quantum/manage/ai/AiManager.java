package com.lunarstra.quantum.manage.ai;

import com.lunarstra.quantum.manage.ai.impl.openrouter.OpenRouterAi;
import com.lunarstra.quantum.manage.ai.impl.qwen.QwenAi;
import com.lunarstra.quantum.manage.ai.impl.spark.SparkAi;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gyyst
 * @Description AI服务统一入口，实现不同厂商AI的注册和调用
 * @Create by 2025/4/29 00:02
 */
@Slf4j
@Component
public class AiManager {
    // AI实现类注册表，根据AiType存储对应的实现类
    private final Map<String, AiTemplate> aiRegistry = new HashMap<>();

    @Resource
    private SparkAi sparkAi;

    @Resource
    private QwenAi qwenAi;

    @Resource
    private OpenRouterAi openRouterAi;

    /**
     * 初始化注册所有AI实现类
     */
    @PostConstruct
    private void init() {
        // 注册各个厂商的AI实现
        registerAiProvider("spark", sparkAi);
        registerAiProvider("qwen", qwenAi);
        registerAiProvider("openrouter", openRouterAi);
        log.info("AI服务注册完成，当前支持的AI厂商: {}", aiRegistry.keySet());
    }

    /**
     * 注册AI提供商
     *
     * @param owner      AI厂商标识
     * @param aiTemplate AI实现类
     */
    private void registerAiProvider(String owner, AiTemplate aiTemplate) {
        aiRegistry.put(owner, aiTemplate);
    }

    /**
     * 根据AI类型获取对应的AI实现
     *
     * @param aiType AI类型
     * @return AI实现类
     */
    public AiTemplate getAiProvider(AiType aiType) {
        AiTemplate aiTemplate = aiRegistry.get(aiType.getOwner());
        if (aiTemplate == null) {
            throw new IllegalArgumentException("不支持的AI厂商: " + aiType.getOwner());
        }
        return aiTemplate;
    }

}
