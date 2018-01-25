package com.binwang.frontOfBinwang.luckDraw.redis;

import com.binwang.frontOfBinwang.utils.HandleDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by yy on 17/7/20.
 */
@Repository
public class LuckRAOImpl implements LuckRAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(LuckRAOImpl.class);

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private final String PREFIX = "fbinwang158:luckDraw:";


    @Override
    public Boolean judgeDraw(String openId, int time) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String key = PREFIX + openId;
        if (redisTemplate.hasKey(key)) {
            int newTime = Integer.parseInt(ops.get(key));
            if (newTime > 0)
                return true;
            else {
                return false;
            }
        } else {
            ops.set(key, time + "", (HandleDateUtil.getTimesnight() - System.currentTimeMillis()) / 1000, TimeUnit.SECONDS);
            return true;
        }
    }

    @Override
    public void decrDrawTime(String openId) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String key = PREFIX + openId;
        ops.increment(key, -1);
    }
}
