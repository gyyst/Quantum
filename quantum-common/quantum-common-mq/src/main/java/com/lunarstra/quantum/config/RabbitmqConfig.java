package com.lunarstra.quantum.config;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.lunarstra.quantum.constant.system.SystemConstant;
import org.slf4j.MDC;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Correlation;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.config.ContainerCustomizer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gyyst
 * @Description
 * @Create by 2023/3/1 19:36
 */
@Configuration
public class RabbitmqConfig {
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 用于接收消息时处理traceId和SpanId
     *
     * @param configurer
     * @param connectionFactory
     * @param simpleContainerCustomizer
     * @return
     */
    @Bean(name = {"rabbitListenerContainerFactory"})
    @ConditionalOnProperty(prefix = "spring.rabbitmq.listener", name = {"type"}, havingValue = "simple",
        matchIfMissing = true)
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(
        SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory,
        ObjectProvider<ContainerCustomizer<SimpleMessageListenerContainer>> simpleContainerCustomizer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        simpleContainerCustomizer.ifUnique(factory::setContainerCustomizer);

        factory.setAfterReceivePostProcessors(message -> {
            Object traceId = message.getMessageProperties().getHeader(SystemConstant.TRACE_ID);
            if (StrUtil.isEmptyIfStr(traceId)) {
                traceId = IdUtil.fastSimpleUUID();
            }
            MDC.put(SystemConstant.TRACE_ID, String.valueOf(traceId));
            MDC.put(SystemConstant.SPAN_ID, IdUtil.fastSimpleUUID());
            return message;
        });
        return factory;
    }

    /**
     * 用于发送消息时处理TraceId
     *
     * @param configurer
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(RabbitTemplateConfigurer configurer, ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate();
        configurer.configure(template, connectionFactory);

        template.setBeforePublishPostProcessors(new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                // 这个方法没调用，调用的是下面那个方法
                return message;
            }

            @Override
            public Message postProcessMessage(Message message, Correlation correlation, String exchange,
                String routingKey) {
                String traceId = MDC.get(SystemConstant.TRACE_ID);

                if (StrUtil.isEmpty(traceId)) {
                    traceId = IdUtil.fastSimpleUUID();
                }

                message.getMessageProperties().setHeader(SystemConstant.TRACE_ID, traceId);

                return message;
            }
        });
        return template;
    }
}
