package com.lunarstra.quantum.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * spring redis 工具类
 **/
@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component
public class RedisCache {
    @Resource
    public RedisTemplate redisTemplate;

    /**
     * 构建redisKey -> redisConstant:keys[0]:keys[1] ... keys[n-1]
     *
     * @param redisConstant
     * @param keys
     * @return
     */
    public static String buildKeyWithConstant(String redisConstant, String... keys) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = keys.length;
        stringBuilder.append(redisConstant);
        for (int i = 0; i < n - 1; i++) {
            stringBuilder.append(keys[i]);
            stringBuilder.append(":");
        }
        stringBuilder.append(keys[n - 1]);
        return stringBuilder.toString();
    }

    /**
     * 构建redisKey -> redisConstant:keys[0]:keys[1] ... keys[n-1]
     *
     * @param redisConstant
     * @param keys
     * @return
     */
    public static String buildKeyWithConstant(String redisConstant, long... keys) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = keys.length;
        stringBuilder.append(redisConstant);
        for (int i = 0; i < n - 1; i++) {
            stringBuilder.append(keys[i]);
            stringBuilder.append(":");
        }
        stringBuilder.append(keys[n - 1]);
        return stringBuilder.toString();
    }

    /**
     * 构建redisKey -> keys[0]:keys[1] ... keys[n-1]
     *
     * @param keys
     * @return
     */
    public static String buildKey(String... keys) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = keys.length;
        for (int i = 0; i < n - 1; i++) {
            stringBuilder.append(keys[i]);
            stringBuilder.append(":");
        }
        stringBuilder.append(keys[n - 1]);
        return stringBuilder.toString();
    }

    /**
     * 设置bitMap
     *
     * @param key
     * @param offset
     * @param value
     * @return
     */

    public boolean setBitMap(String key, long offset, boolean value) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setBit(key, offset, value));
    }

    /**
     * 获得bitMap
     *
     * @param key
     * @param digitCapacity 总共获取几位
     * @param offset        从第几位开始获取
     * @return
     */
    public Long getBitMap(String key, long offset, int digitCapacity) {
        List<Long> result = redisTemplate.opsForValue()
            .bitField(key, BitFieldSubCommands.create()
                .get(BitFieldSubCommands.BitFieldType.unsigned(digitCapacity))
                .valueAt(offset));
        if (result == null) {
            return null;
        }
        return result.get(0);
    }

    /**
     * 获取某一位的bitMap
     *
     * @param key
     * @param offset
     * @return
     */
    public boolean getBitMap(String key, long offset) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().getBit(key, offset));
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final long timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, unit));
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean deleteObject(final String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    public long deleteObject(final Collection collection) {
        return redisTemplate.delete(collection);
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> long setCacheList(final String key, final List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> long setCacheList(final String key, final List<T> dataList, final long timeout, final TimeUnit unit) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        redisTemplate.expire(key, timeout, unit);
        long l = count == null ? 0 : count;
        return l;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public <T> T getCacheMapValue(final String key, final String hKey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * 删除Hash中的数据
     *
     * @param key
     */
    public void delCacheMapValue(final String key, final String hkey) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, hkey);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    public void registerSaveRedis(String name, String passPassword, String email) {
        redisTemplate.opsForValue().set(name, passPassword);
        redisTemplate.opsForValue().set(email, passPassword);
    }

    public void loginSaveRedis(String name, String passPassword) {
        redisTemplate.opsForValue().set(name, passPassword);
    }

    /**
     * 验证 key 是否存在
     */
    public Boolean verifyKey(String key) {
        Boolean aBoolean = redisTemplate.hasKey(key);
        return aBoolean;
    }

    public String getKey(String name) {
        Collection<String> passCollectionName = keys(name);
        String passName = null;
        for (String redisQueryUserName : passCollectionName) {
            passName = redisQueryUserName;
        }
        return passName;
    }
}