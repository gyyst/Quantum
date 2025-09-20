package com.lunarstra.quantum.manage.ai.impl.qwen;

import com.lunarstra.quantum.manage.ai.AiType;
import io.github.pigmesh.ai.deepseek.core.DeepSeekClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gyyst
 * @Description 阿里云百炼平台配置类
 * @Create by 2024/6/10 10:00
 */
@Component
public class QwenConfig {
    private static Map<AiType, DeepSeekClient> map = new ConcurrentHashMap<>();

    @Value("${ai.qwen.apikey:666}")
    private String apiKey;

    /**
     * 构建阿里云百炼平台客户端
     *
     * @param type AI类型
     * @return DeepSeekClient
     */
    private DeepSeekClient buildAliClient(AiType type) {
        DeepSeekClient.Builder builder = DeepSeekClient.builder()
            .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
            .model(type.getName())
            .openAiApiKey(apiKey)
            .logRequests(true)
            .logResponses(true);
        return builder.build();
    }

    /**
     * 获取指定类型的阿里云百炼平台客户端
     *
     * @param type AI类型
     * @return DeepSeekClient
     */
    public DeepSeekClient getAliClient(AiType type) {
        return map.computeIfAbsent(type, this::buildAliClient);
    }

    /**
     * 获取默认的阿里云百炼平台客户端
     *
     * @return DeepSeekClient
     */

    public DeepSeekClient getAliClient() {
        return getAliClient(getDefaultAliAiType());
    }

    public AiType getDefaultAliAiType() {
        return AiType.Qwen_Qwq; // 默认使用通义千问-Max模型，适合复杂任务
    }
}
