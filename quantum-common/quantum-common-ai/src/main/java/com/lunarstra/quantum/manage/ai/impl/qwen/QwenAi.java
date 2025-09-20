package com.lunarstra.quantum.manage.ai.impl.qwen;

import com.lunarstra.quantum.manage.ai.AiTemplate;
import com.lunarstra.quantum.manage.ai.AiType;
import io.github.pigmesh.ai.deepseek.core.DeepSeekClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author gyyst
 * @Description 阿里云百炼平台AI实现类
 * @Create by 2024/6/10 10:00
 */
@Slf4j
@Component
public class QwenAi extends AiTemplate {

    @Resource
    private QwenConfig qwenConfig;

    @Override
    protected DeepSeekClient getDefaultClient() {
        return qwenConfig.getAliClient();
    }

    @Override
    protected DeepSeekClient getClient(AiType aiType) {
        // 检查是否为阿里云百炼平台类型的模型
        if (aiType.getOwner().equals("qwen")) {
            return qwenConfig.getAliClient(aiType);
        } else {
            throw new IllegalArgumentException(
                "不支持的AI类型: " + aiType.name() + ", 当前服务仅支持阿里云百炼平台类型");
        }
    }
}
