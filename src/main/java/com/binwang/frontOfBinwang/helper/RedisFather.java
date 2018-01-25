package com.binwang.frontOfBinwang.helper;

import org.slf4j.Logger;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by yy on 17/12/28.
 */
public class RedisFather {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public Logger LOGGER = null;


    protected Boolean writeStringValue(String key, String value, int expire, String errMsg) {
        try {
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            ops.set(key, value);
            System.out.println(value);
            if (expire != -1)
                stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            LOGGER.error(errMsg);
            return false;
        }
    }

    protected Boolean hasStringValue(String key) {
        if (stringRedisTemplate.hasKey(key))
            return true;
        else
            return false;
    }

    protected Boolean deleteStringValue(String key, String errMsg) {
        try {
            if (stringRedisTemplate.hasKey(key))
                stringRedisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            LOGGER.error(errMsg);
            return false;
        }
    }

    protected String getStringValue(String key) {
        if (stringRedisTemplate.hasKey(key)) {
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            return ops.get(key);
        } else
            return "";
    }

    protected Boolean writeHashValue(String key, Map<String, Object> m, int expire, String errMsg) {
        try {
            HashOperations<String, String, Object> ops = redisTemplate.opsForHash();
            ops.putAll(key, m);
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            LOGGER.error(errMsg);
            return false;
        }
    }
}
