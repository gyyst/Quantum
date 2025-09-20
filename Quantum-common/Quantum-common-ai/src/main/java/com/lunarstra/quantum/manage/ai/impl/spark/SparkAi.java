package com.lunarstra.quantum.manage.ai.impl.spark;

import com.lunarstra.quantum.manage.ai.AiTemplate;
import com.lunarstra.quantum.manage.ai.AiType;
import io.github.pigmesh.ai.deepseek.core.DeepSeekClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author gyyst
 * @Description
 * @Create by 2024/3/2 20:19
 */
@Slf4j
@Component
public class SparkAi extends AiTemplate {

    @Resource
    private SparkConfig sparkConfig;

    @Override
    protected DeepSeekClient getDefaultClient() {
        return sparkConfig.getSparkClient();
    }

    @Override
    protected DeepSeekClient getClient(AiType aiType) {
        // 检查是否为Spark类型的模型
        if (aiType.getOwner().equals("spark")) {
            return sparkConfig.getSparkClient(aiType);
        } else {
            throw new IllegalArgumentException("不支持的AI类型: " + aiType.name() + ", 当前服务仅支持Spark类型");
        }
    }
}
