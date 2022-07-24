package com.yiyu.utils;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zh
 * @ClassName : zh.nb.securityToken.util.RedisCache
 * @Description :
 * Created by user on 2021-12-25 17:16:29
 * Copyright  2020 user. All rights reserved.
 */
@Component
@SuppressWarnings({"unchecked", "rawtypes"})
public class RedisCache {

    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 缓存的基本对象。Integer String 实体类
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     * @return 缓存的对象
     */
    public <T> ValueOperations<String, T> setCacheObject(String key, T value) {
        ValueOperations<String, T> operations = redisTemplate.opsForValue();
        operations.set(key, value);
        return operations;
    }

    /**
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     * @return 缓存的对象
     */
    public <T> ValueOperations<String, T> setCacheObject(String key, T value, Integer timeout, TimeUnit timeUnit) {
        ValueOperations<String, T> operations = redisTemplate.opsForValue();
        operations.set(key, value, timeout, timeUnit);
        return operations;
    }

    /**
     * 获得缓存的基本对象
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(String key) {
        ValueOperations<String, T> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    /**
     * 删除单个对象
     */
    public void deleteObject(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 对指定的key进行自增
     *
     * @param key
     */
    public void incrby(String key) {
        redisTemplate.opsForValue().increment(key);
    }

    /**
     * 删除集合对象
     */
    public void deleteObject(Collection collection) {
        redisTemplate.delete(collection);
    }


    /**
     * 缓存list数据
     *
     * @param key      缓存的键值
     * @param dataList 带缓存的list数据
     * @return 缓存的对象
     */
    public <T> ListOperations<String, T> setCacheList(String key, List<T> dataList) {
        ListOperations<String, T> listOperations = redisTemplate.opsForList();
        if (dataList != null) {
            for (T t : dataList) {
                listOperations.leftPush(key, t);
            }
        }
        return listOperations;
    }


    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的集合数据
     */
    public <T> List<T> getCacheList(String key) {
        List<T> list = new ArrayList<>();
        ListOperations<String, T> listOperations = redisTemplate.opsForList();
        Long size = listOperations.size(key);
        assert size != null;
        for (int i = 0; i < size; i++) {
            list.add(listOperations.index(key, i));
        }
        return list;
    }


    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(String key, Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        for (T t : dataSet) {
            setOperation.add(t);
        }
        return setOperation;
    }

    public <T> BoundSetOperations<String, T> setCacheSet(String key, T value) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        setOperation.add(value);
        return setOperation;
    }

    /**
     * 设置当前set集合中的key过期时间
     *
     * @param key:
     * @param value:
     * @param timeout:
     * @param timeUnit:
     * @return org.springframework.data.redis.core.BoundSetOperations<java.lang.String, T> :
     * @author zh
     * @date 2022/4/29 22:24
     **/
    public <T> BoundSetOperations<String, T> setCacheSet(String key, T value, Integer timeout, TimeUnit timeUnit) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        setOperation.add(value);
        setOperation.expire(timeout, timeUnit);
        return setOperation;
    }

    /**
     * 获得缓存的set
     */
    public <T> Set<T> getCacheSet(String key) {
        Set<T> dataSet;
        BoundSetOperations<String, T> operation = redisTemplate.boundSetOps(key);
        dataSet = operation.members();
        return dataSet;
    }

    public <T> boolean sisMember(String key, T value) {
        boolean dataSet;
        BoundSetOperations<String, T> operation = redisTemplate.boundSetOps(key);
        dataSet = operation.isMember(value);
        return dataSet;
    }


    /**
     * 缓存Map
     */
    public <T> HashOperations<String, String, T> setCacheMap(String key, Map<String, T> dataMap) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        if (null != dataMap) {
            for (Map.Entry<String, T> entry : dataMap.entrySet()) {
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }
        }
        return hashOperations;
    }

    /**
     * 缓存Map
     */
    public <T> HashOperations<String, String, T> setCacheMap(String key, String hashKey, T value) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key, hashKey, value);
        return hashOperations;
    }

    /**
     * 缓存Map
     */
    public <T> HashOperations<String, String, T> setAllCacheMap(String key, Map<String, T> dataMap) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        if (null != dataMap) {
            hashOperations.putAll(key, dataMap);
        }
        return hashOperations;
    }

    /**
     * 获得缓存的Map中key的值
     */
    public <T> T getCacheMap(String key, String hashKey) {

        return (T) redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 获得缓存的Map
     */
    public <T> Map<String, T> getCacheMap(String key) {

        return (Map<String, T>) redisTemplate.opsForHash().entries(key);
    }

    /**
     * 对map中的一个值进行自增
     */
    public HashOperations<String, String, T> incrMapKey(String key, String hashKey, Long incrCount) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.increment(key, hashKey, incrCount);
        return hashOperations;
    }

    /**
     * 判断key是否存在
     */
    public Boolean exist(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     */
    public Collection<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }
}
