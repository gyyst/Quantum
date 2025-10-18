package com.lunarstra.quantum.handler;

import love.forte.simbot.component.onebot.v11.core.event.message.OneBotFriendMessageEvent;
import love.forte.simbot.event.ChatGroupMessageEvent;
import love.forte.simbot.quantcat.common.annotations.ContentTrim;
import love.forte.simbot.quantcat.common.annotations.Filter;
import love.forte.simbot.quantcat.common.annotations.Listener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class MyHandles {
    /**
     * 此处是一个标准库中通用的类型：聊天群消息事件
     */
    @Listener
    public void onGroupMessage(ChatGroupMessageEvent event) {
        System.out.println("ChatGroupMessageEvent: " + event);
    }

    /**
     * 此处监听的是OneBot组件中的专属类型：OneBot的好友消息事件
     * 并且过滤消息：消息中的文本消息去除前后空字符后，等于 '你好'
     */
    @Listener
    @ContentTrim
    @Filter("你好")
    public CompletableFuture<?> onFriendMessage(OneBotFriendMessageEvent event) {
        System.out.println("OneBotFriendMessageEvent: " + event);
        return event.replyAsync("你也好");
        // 可以直接返回任意 CompletableFuture 类型
    }
}