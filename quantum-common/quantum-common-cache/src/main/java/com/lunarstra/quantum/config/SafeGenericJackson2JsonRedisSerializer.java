package com.lunarstra.quantum.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * 安全的GenericJackson2JsonRedisSerializer实现
 * 用于替代已废弃的GenericJackson2JsonRedisSerializer
 */
public class SafeGenericJackson2JsonRedisSerializer implements RedisSerializer<Object> {

    private final ObjectMapper objectMapper;

    /**
     * Creates {@link SafeGenericJackson2JsonRedisSerializer} and configures {@link ObjectMapper} for default typing.
     */
    public SafeGenericJackson2JsonRedisSerializer() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.activateDefaultTyping(
            LaissezFaireSubTypeValidator.instance,
            ObjectMapper.DefaultTyping.NON_FINAL
        );
        this.objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    }

    /**
     * Creates {@link SafeGenericJackson2JsonRedisSerializer} and configures {@link ObjectMapper} for default typing.
     *
     * @param objectMapper must not be {@literal null}.
     */
    public SafeGenericJackson2JsonRedisSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public byte[] serialize(Object source) throws SerializationException {
        if (source == null) {
            return new byte[0];
        }

        try {
            return objectMapper.writeValueAsBytes(source);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Could not write JSON: " + e.getMessage(), e);
        }
    }

    @Override
    public Object deserialize(byte[] source) throws SerializationException {
        if (source == null || source.length == 0) {
            return null;
        }

        try {
            return objectMapper.readValue(source, Object.class);
        } catch (Exception e) {
            throw new SerializationException("Could not read JSON: " + e.getMessage(), e);
        }
    }
}