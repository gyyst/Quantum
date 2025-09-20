package com.lunarstra.quantum.manage.ai;

import lombok.Getter;

/**
 * @author gyyst
 * @Description AI类型枚举，整合了OpenRouter、Spark和阿里云百炼平台的模型
 * @Create by 2025/4/27 15:29
 */
@Getter
public enum AiType {
    // OpenRouter模型
    OpenRouter_DeepseekV3("openrouter", "deepseek/deepseek-chat-v3-0324:free"),
    OpenRouter_DeepseekR1("openrouter", "deepseek/deepseek-r1:free"),
    OpenRouter_DSr1t_chimera("openrouter", "tngtech/deepseek-r1t-chimera:free"),

    // Spark模型
    Spark_40Ultra("spark", "4.0Ultra"),
    Spark_Max("spark", "generalv3.5"),
    Spark_Max32K("spark", "max-32k"),
    Spark_Pro("spark", "generalv3"),
    Spark_Pro128K("spark", "pro-128k"),
    Spark_Lite("spark", "lite"),

    // 阿里云百炼平台模型
    Qwen_Max("qwen", "qwen-max-latest"), // 通义千问-Max，适合复杂任务，能力最强
    Qwen_Plus("qwen", "qwen-plus-latest"), // 通义千问-Plus，效果、速度、成本均衡
    Qwen_Turbo("qwen", "qwen-turbo-latest"), // 通义千问-Turbo，适合简单任务，速度快、成本极低
    Qwen_Long("qwen", "qwen-long-latest"), // 通义千问-Long，适合大规模文本分析，最大上下文长度可达1000万token
    Qwen_Omni("qwen", "qwen-omni-latest"), // 通义千问全模态模型
    Qwen_VL("qwen", "qwen-vl-max-latest"), // 通义千问视觉理解模型
    Qwen_Audio("qwen", "qwen-audio-turbo-latest"), // 通义千问音频理解模型
    Qwen_Qwq("qwen", "qwq-32b"), // QwQ模型，基于Qwen2.5训练，推理能力强
    Qwen_Qwen3("qwen", "qwen3-235b-a22b"); //Qwen3

    private String owner;
    private String name;

    AiType(String owner, String name) {
        this.owner = owner;
        this.name = name;
    }

}
