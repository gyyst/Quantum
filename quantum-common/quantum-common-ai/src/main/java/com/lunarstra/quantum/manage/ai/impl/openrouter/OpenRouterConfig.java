package com.lunarstra.quantum.manage.ai.impl.openrouter;

import com.lunarstra.quantum.manage.ai.AiType;
import io.github.pigmesh.ai.deepseek.core.DeepSeekClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gyyst
 * @Description
 * @Create by 2025/4/27 11:30
 */
@Component
public class OpenRouterConfig {
    private static Map<AiType, DeepSeekClient> map = new ConcurrentHashMap<>();

    @Value("${ai.openrouter.apikey:666}")
    private String openRouterApiKey;

    private DeepSeekClient buildOpenRouterClient(AiType type) {
        DeepSeekClient.Builder builder = DeepSeekClient.builder()
            .baseUrl("https://openrouter.ai/api/v1")
            .model(type.getName())
            .openAiApiKey(openRouterApiKey)
            .logRequests(true)
            .logResponses(true);
        return builder.build();
    }

    public DeepSeekClient getOpenRouterClient(AiType type) {
        return map.computeIfAbsent(type, this::buildOpenRouterClient);
    }

    public DeepSeekClient getOpenRouterClient() {
        return getOpenRouterClient(getDefaultOpenRouterAitype());
    }

    public AiType getDefaultOpenRouterAitype() {
        return AiType.OpenRouter_DSr1t_chimera;
    }
}
