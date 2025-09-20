package com.lunarstra.quantum.manage.ai.impl.openrouter;

import com.lunarstra.quantum.manage.ai.AiTemplate;
import com.lunarstra.quantum.manage.ai.AiType;
import io.github.pigmesh.ai.deepseek.core.DeepSeekClient;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author gyyst
 * @Description
 * @Create by 2025/4/27 11:43
 */
@Component
public class OpenRouterAi extends AiTemplate {
    @Resource
    private OpenRouterConfig openRouterConfig;

    @Override
    protected DeepSeekClient getDefaultClient() {
        return openRouterConfig.getOpenRouterClient();
    }

    /**
     * 获取默认客户端
     *
     * @return DeepSeekClient
     */
    @Override
    protected AiType getDefaultAiType() {
        return openRouterConfig.getDefaultOpenRouterAitype();
    }

    @Override
    protected DeepSeekClient getClient(AiType aiType) {
        // 检查是否为OpenRouter类型的模型
        if (aiType.getOwner().equals("openrouter")) {
            return openRouterConfig.getOpenRouterClient(aiType);
        } else {
            throw new IllegalArgumentException("不支持的AI类型: " + aiType.name() + ", 当前服务仅支持OpenRouter类型");
        }
    }

}
