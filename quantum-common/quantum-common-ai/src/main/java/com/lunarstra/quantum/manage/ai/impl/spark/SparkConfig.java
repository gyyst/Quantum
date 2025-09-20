package com.lunarstra.quantum.manage.ai.impl.spark;

import com.lunarstra.quantum.manage.ai.AiType;
import io.github.pigmesh.ai.deepseek.core.DeepSeekClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gyyst
 * @Description
 * @Create by 2024/5/27 上午10:58
 */
@Configuration
public class SparkConfig {
    private static Map<AiType, DeepSeekClient> map = new ConcurrentHashMap<>();

    @Value("${ai.spark.apikey:666}")
    private String sparkApiKey;

    private DeepSeekClient buildSparkClient(AiType type) {
        DeepSeekClient.Builder builder = DeepSeekClient.builder()
            .baseUrl("https://spark-api-open.xf-yun.com/v1/")
            .model(type.getName())
            .openAiApiKey(sparkApiKey)
            .logRequests(true)
            .logResponses(true);
        return builder.build();
    }

    public DeepSeekClient getSparkClient(AiType type) {
        return map.computeIfAbsent(type, this::buildSparkClient);
    }

    public DeepSeekClient getSparkClient() {
        return getSparkClient(getDefaultSparkAitype());
    }

    public AiType getDefaultSparkAitype() {
        return AiType.Spark_Lite;
    }
}
