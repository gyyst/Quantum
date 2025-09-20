package com.lunarstra.quantum.manage.ai;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import io.github.pigmesh.ai.deepseek.core.DeepSeekClient;
import io.github.pigmesh.ai.deepseek.core.chat.ChatCompletionRequest;
import io.github.pigmesh.ai.deepseek.core.chat.ChatCompletionResponse;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gyyst
 * @Description
 * @Create by 2023/6/14 15:31
 */

public abstract class AiTemplate {

    // 存储AI调用前钩子（线程安全）
    private static final ThreadLocal<List<BeforeAiCallHook>> beforeHooksThreadLocal = ThreadLocal.withInitial(
        ArrayList::new);

    // 存储AI调用后钩子（线程安全）
    private static final ThreadLocal<List<AfterAiCallHook>> afterHooksThreadLocal = ThreadLocal.withInitial(
        ArrayList::new);

    /**
     * 清除ThreadLocal资源，防止内存泄漏
     * 应在线程结束时调用此方法
     */
    public static void removeThreadLocalHooks() {
        beforeHooksThreadLocal.remove();
        afterHooksThreadLocal.remove();
    }

    /**
     * 获取当前线程的前置钩子列表
     *
     * @return 前置钩子列表
     */
    private List<BeforeAiCallHook> getBeforeHooks() {
        return beforeHooksThreadLocal.get();
    }

    /**
     * 获取当前线程的后置钩子列表
     *
     * @return 后置钩子列表
     */
    private List<AfterAiCallHook> getAfterHooks() {
        return afterHooksThreadLocal.get();
    }

    /**
     * 获取默认客户端
     *
     * @return DeepSeekClient
     */
    protected DeepSeekClient getDefaultClient() {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取默认客户端
     *
     * @return DeepSeekClient
     */
    protected AiType getDefaultAiType() {
        throw new UnsupportedOperationException();
    }

    /**
     * 根据AI类型获取客户端
     *
     * @param aiType AI类型
     * @return DeepSeekClient
     */
    protected DeepSeekClient getClient(AiType aiType) {
        throw new UnsupportedOperationException();
    }

    /**
     * 无状态对话
     *
     * @param content 对话内容
     * @return 回复内容
     */
    public String doChat(String content) {
        // 执行调用前钩子
        executeBeforeHooks(content, null, getDefaultAiType());

        DeepSeekClient client = getDefaultClient();

        ChatCompletionRequest request = ChatCompletionRequest.builder()
            // 添加用户消息
            .addUserMessage(content).build();
        ChatCompletionResponse chatCompletionResponse = client.chatCompletion(request).execute();
        String response = chatCompletionResponse.content();
        // 执行调用后钩子
        executeAfterHooks(content, null, getDefaultAiType(), chatCompletionResponse);

        return response;
    }

    /**
     * 根据AI类型进行无状态对话
     *
     * @param content 对话内容
     * @param aiType  AI类型
     * @return 回复内容
     */
    public String doChat(String content, AiType aiType) {
        // 执行调用前钩子
        executeBeforeHooks(content, null, aiType);

        DeepSeekClient client = getClient(aiType);

        ChatCompletionRequest request = ChatCompletionRequest.builder()
            // 添加用户消息
            .addUserMessage(content).build();
        ChatCompletionResponse chatCompletionResponse = client.chatCompletion(request).execute();
        String response = chatCompletionResponse.content();
        // 执行调用后钩子
        executeAfterHooks(content, null, aiType, chatCompletionResponse);

        return response;
    }

    /**
     * 无状态对话
     *
     * @param content 对话内容
     * @param prompt  提示词
     * @return 回复内容
     */
    public String doChat(String content, String prompt) {
        // 执行调用前钩子
        executeBeforeHooks(content, prompt, null);

        DeepSeekClient client = getDefaultClient();
        ChatCompletionRequest request = ChatCompletionRequest.builder()
            // 模型选择，支持 DEEPSEEK_CHAT、DEEPSEEK_REASONER 等
            // 添加提示词
            .addSystemMessage(prompt)
            // 添加用户消息
            .addUserMessage(content).build();
        ChatCompletionResponse chatCompletionResponse = client.chatCompletion(request).execute();
        String response = chatCompletionResponse.content();
        // 执行调用后钩子
        executeAfterHooks(content, prompt, getDefaultAiType(), chatCompletionResponse);

        return response;
    }

    /**
     * 根据AI类型进行无状态对话
     *
     * @param content 对话内容
     * @param prompt  提示词
     * @param aiType  AI类型
     * @return 回复内容
     */
    public String doChat(String content, String prompt, AiType aiType) {
        // 执行调用前钩子
        executeBeforeHooks(content, prompt, aiType);

        DeepSeekClient client = getClient(aiType);
        ChatCompletionRequest request = ChatCompletionRequest.builder()
            // 添加提示词
            .addSystemMessage(prompt)
            // 添加用户消息
            .addUserMessage(content).build();
        ChatCompletionResponse chatCompletionResponse = client.chatCompletion(request).execute();
        String response = chatCompletionResponse.content();
        // 执行调用后钩子
        executeAfterHooks(content, prompt, aiType, chatCompletionResponse);

        return response;
    }

    /**
     * 流式返回
     *
     * @param content 对话内容
     * @return 流式回复
     */
    public Flux<ChatCompletionResponse> doChatWithFlux(String content) {
        // 执行调用前钩子
        executeBeforeHooks(content, null, null);

        Flux<ChatCompletionResponse> responseFlux = getDefaultClient().chatFluxCompletion(content);

        StringBuilder fullResponse = new StringBuilder();
        // 使用doOnComplete来在流完成时执行调用后钩子
        return responseFlux.doOnNext(response -> {
            extracted(content, null, getDefaultAiType(), response, fullResponse);
        });
    }

    /**
     * 根据AI类型进行流式对话
     *
     * @param content 对话内容
     * @param aiType  AI类型
     * @return 流式回复
     */
    public Flux<ChatCompletionResponse> doChatWithFlux(String content, AiType aiType) {
        // 执行调用前钩子
        executeBeforeHooks(content, null, aiType);

        Flux<ChatCompletionResponse> responseFlux = getClient(aiType).chatFluxCompletion(content);

        StringBuilder fullResponse = new StringBuilder();
        // 使用doOnComplete来在流完成时执行调用后钩子
        return responseFlux.doOnNext(response -> {
            extracted(content, null, aiType, response, fullResponse);
        });
    }

    /**
     * 流式返回
     *
     * @param content 对话内容
     * @param prompt  提示词
     * @return 流式回复
     */
    public Flux<ChatCompletionResponse> doChatWithFlux(String content, String prompt) {
        // 执行调用前钩子
        executeBeforeHooks(content, prompt, getDefaultAiType());

        ChatCompletionRequest request = ChatCompletionRequest.builder()
            // 添加提示词
            .addSystemMessage(prompt)
            // 添加用户消息
            .addUserMessage(content).build();
        Flux<ChatCompletionResponse> responseFlux = getDefaultClient().chatFluxCompletion(request);
        StringBuilder fullResponse = new StringBuilder();
        // 使用doOnComplete来在流完成时执行调用后钩子
        return responseFlux.doOnNext(response -> {
            extracted(content, prompt, getDefaultAiType(), response, fullResponse);
        });
    }

    /**
     * 根据AI类型进行流式对话
     *
     * @param content 对话内容
     * @param prompt  提示词
     * @param aiType  AI类型
     * @return 流式回复
     */
    public Flux<ChatCompletionResponse> doChatWithFlux(String content, String prompt, AiType aiType) {
        // 执行调用前钩子
        executeBeforeHooks(content, prompt, aiType);

        ChatCompletionRequest request = ChatCompletionRequest.builder()
            // 添加提示词
            .addSystemMessage(prompt)
            // 添加用户消息
            .addUserMessage(content).build();
        Flux<ChatCompletionResponse> responseFlux = getClient(aiType).chatFluxCompletion(request);
        StringBuilder fullResponse = new StringBuilder();
        // 使用doOnComplete来在流完成时执行调用后钩子
        return responseFlux.doOnNext(response -> {
            extracted(content, prompt, aiType, response, fullResponse);
        });
    }

    /**
     * 统一流式后缀
     *
     * @param content
     * @param prompt
     * @param aiType
     * @param response
     * @param fullResponse
     */
    private void extracted(String content, String prompt, AiType aiType, ChatCompletionResponse response,
        StringBuilder fullResponse) {
        String word = StrUtil.emptyToDefault(response.content(), "");
        fullResponse.append(word);

        if (StrUtil.isEmpty(response.choices().getFirst().finishReason())) {
            return;
        }
        ReflectUtil.setFieldValue(response.choices().getFirst().delta(), "content", fullResponse);
        executeAfterHooks(content, prompt, aiType, response);
    }

    /**
     * 注册AI调用前钩子
     *
     * @param hook 钩子函数
     */
    public void registerBeforeHook(BeforeAiCallHook hook) {
        if (hook != null) {
            getBeforeHooks().add(hook);
        }
    }

    /**
     * 注册AI调用后钩子
     *
     * @param hook 钩子函数
     */
    public void registerAfterHook(AfterAiCallHook hook) {
        if (hook != null) {
            getAfterHooks().add(hook);
        }
    }

    /**
     * 移除AI调用前钩子
     *
     * @param hook 钩子函数
     */
    public void removeBeforeHook(BeforeAiCallHook hook) {
        getBeforeHooks().remove(hook);
    }

    /**
     * 移除AI调用后钩子
     *
     * @param hook 钩子函数
     */
    public void removeAfterHook(AfterAiCallHook hook) {
        getAfterHooks().remove(hook);
    }

    /**
     * 清除当前线程的所有钩子
     */
    public void clearHooks() {
        getBeforeHooks().clear();
        getAfterHooks().clear();
    }

    /**
     * 执行所有AI调用前钩子
     *
     * @param content 对话内容
     * @param prompt  提示词
     * @param aiType  AI类型
     */
    protected void executeBeforeHooks(String content, String prompt, AiType aiType) {
        for (BeforeAiCallHook hook : getBeforeHooks()) {
            hook.beforeCall(content, prompt, aiType);
        }
    }

    /**
     * 执行所有AI调用后钩子
     *
     * @param content  对话内容
     * @param prompt   提示词
     * @param aiType   AI类型
     * @param response AI响应结果
     */
    protected void executeAfterHooks(String content, String prompt, AiType aiType, ChatCompletionResponse response) {
        for (AfterAiCallHook hook : getAfterHooks()) {
            hook.afterCall(content, prompt, aiType, response);
        }
    }

    /**
     * AI调用前钩子接口
     */
    public interface BeforeAiCallHook {
        /**
         * 在AI调用前执行
         *
         * @param content 对话内容
         * @param prompt  提示词（可能为null）
         * @param aiType  AI类型（可能为null）
         */
        void beforeCall(String content, String prompt, AiType aiType);
    }

    /**
     * AI调用后钩子接口
     */
    public interface AfterAiCallHook {
        /**
         * 在AI调用后执行
         *
         * @param content  对话内容
         * @param prompt   提示词（可能为null）
         * @param aiType   AI类型（可能为null）
         * @param response AI响应结果
         */
        void afterCall(String content, String prompt, AiType aiType, ChatCompletionResponse response);
    }
}
