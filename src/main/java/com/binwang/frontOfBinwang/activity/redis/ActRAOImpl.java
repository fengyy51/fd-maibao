package com.binwang.frontOfBinwang.activity.redis;

import com.binwang.frontOfBinwang.helper.RedisFather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by yy on 17/6/29.
 */
@Repository
public class ActRAOImpl extends RedisFather implements ActRAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActRAOImpl.class);

    private final String PREFIX_SIGHED = "fbinwang158:act:signedNum:";


    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public ActRAOImpl() {
        super.LOGGER = LOGGER;
    }

    @Override
    public int getSignedNum(long actId) {
        String key = PREFIX_SIGHED + actId;
        if (super.hasStringValue(key))
            return Integer.parseInt(super.getStringValue(key));
        else
            return -1;
    }

    @Override
    public Boolean writeSignedNum(long actId) {
        String key = PREFIX_SIGHED + actId;
        System.out.println(key);
        return super.writeStringValue(key, "0", -1, "初始化活动已报名人数至Redis出错,actId为:" + actId);
    }

    @Override
    public Boolean addSignedNum(long actId, int limit) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String key = PREFIX_SIGHED + actId;
        int cur = Integer.parseInt(ops.get(key));
        if (cur >= limit)
            return false;
        ops.increment(key, 1);
        return true;
    }

    @Override
    public Boolean minusSignedNum(long actId) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String key = PREFIX_SIGHED + actId;
        ops.increment(key, -1);
        return true;
    }
}
