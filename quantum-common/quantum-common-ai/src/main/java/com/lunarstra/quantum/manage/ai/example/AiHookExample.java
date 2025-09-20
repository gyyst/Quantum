package com.lunarstra.quantum.manage.ai.example;

import com.lunarstra.quantum.manage.ai.AiManager;
import com.lunarstra.quantum.manage.ai.AiTemplate;
import com.lunarstra.quantum.manage.ai.AiType;
import com.lunarstra.quantum.manage.ai.impl.qwen.QwenAi;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * AI钩子函数使用示例
 *
 * @author gyyst
 * @Create by 2024/6/20 10:00
 */
@Slf4j
@Component
public class AiHookExample {

    @Resource
    private QwenAi qwenAi;

    @Resource
    private AiManager aiManager;

    /**
     * 初始化并注册钩子函数
     */
    public void initHooks() {

        AiTemplate aiTemplate = aiManager.getAiProvider(AiType.OpenRouter_DSr1t_chimera);
        // 注册调用前钩子
        aiTemplate.registerBeforeHook((content, prompt, aiType) -> {
            log.info("AI调用前钩子执行 - 内容: {}, 提示词: {}, AI类型: {}",
                    content,
                    prompt != null ? prompt : "无",
                    aiType != null ? aiType.name() : "默认");
            // 可以在这里进行内容审核、日志记录等操作
        });

        // 注册调用后钩子
        aiTemplate.registerAfterHook((content, prompt, aiType, response) -> {
            log.info("AI调用后钩子执行 - 内容: {}, 提示词: {}, AI类型: {}, 响应: {}",
                    content,
                    prompt != null ? prompt : "无",
                    aiType != null ? aiType.name() : "默认",
                    response);
            // 可以在这里进行响应内容分析、统计等操作
        });
    }

    /**
     * 使用带有钩子函数的AI对话示例
     */
    public void demoWithHooks() {
        // 初始化钩子
        initHooks();

        // 进行AI对话，钩子函数会在对话前后自动执行
        String response = qwenAi.doChat("你好，请介绍一下自己");
        log.info("AI回复: {}", response);

        // 使用提示词的对话
        response = qwenAi.doChat("写一首关于春天的诗", "你是一位著名诗人");
        log.info("带提示词的AI回复: {}", response);

        // 使用特定AI类型的对话
        response = qwenAi.doChat("解释量子力学的基本原理", AiType.Qwen_Qwq);
        log.info("特定AI类型的回复: {}", response);

        // 清除所有钩子
        qwenAi.clearHooks();
        log.info("已清除所有钩子函数");
    }

    /**
     * 自定义钩子函数示例 - 内容审核
     */
    public void customContentFilterHook() {
        // 注册内容审核钩子
        AiTemplate.BeforeAiCallHook contentFilter = (content, prompt, aiType) -> {
            // 检查内容是否包含敏感词
            if (content.contains("敏感词")) {
                log.warn("检测到敏感内容，已拦截请求: {}", content);
                throw new IllegalArgumentException("请求内容包含敏感词，已被拦截");
            }
        };

        qwenAi.registerBeforeHook(contentFilter);

        try {
            // 正常内容
            String response = qwenAi.doChat("这是正常内容");
            log.info("正常内容回复: {}", response);

            // 包含敏感词的内容
            response = qwenAi.doChat("这是包含敏感词的内容");
            log.info("敏感内容回复: {}", response); // 这行不会执行，因为上面会抛出异常
        } catch (Exception e) {
            log.error("AI调用异常: {}", e.getMessage());
        } finally {
            // 移除特定的钩子
            qwenAi.removeBeforeHook(contentFilter);
        }
    }

}
