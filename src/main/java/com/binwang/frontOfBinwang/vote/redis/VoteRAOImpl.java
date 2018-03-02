package com.binwang.frontOfBinwang.vote.redis;

import com.binwang.frontOfBinwang.helper.RedisFather;
import com.binwang.frontOfBinwang.utils.HandleDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by yy .
 */
@Repository
public class VoteRAOImpl extends RedisFather implements VoteRAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(VoteRAOImpl.class);

    public VoteRAOImpl() {
        super.LOGGER = LOGGER;
    }

    private final String AUTH_PRE = "fbinwang158:refresh_token:";
    private final String VOTE_TIME_PRE = "fbinwang158:vote:per:";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<String, String> redisTemplate;


//    @Value("${fbinwang.vote.time}")
//    private int votePerTime;

    @Override
    public Boolean judgeAuthOpenId(String openId,long actId) {
        String key = AUTH_PRE + openId+String.valueOf(actId);
        return super.hasStringValue(key);
    }
    @Override
    public Boolean judgeIp(String ip,long actId) {
        String key = AUTH_PRE + ip+String.valueOf(actId);
        return super.hasStringValue(key);
    }
    @Override
    public Boolean judgeIpVote(String ip,int voteMax,long actId) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String key = VOTE_TIME_PRE + ip+String.valueOf(actId);
        if (redisTemplate.hasKey(key)) {
            int newTime = Integer.parseInt(ops.get(key));
            LOGGER.info(String.valueOf(newTime)+"newtime");
            if (newTime <voteMax) {
                return true;
            } else {
                return false;
            }
        } else {
            // 设置缓存时间 当天有效
            ops.set(key, 1 + "", (HandleDateUtil.getTimesnight() - System.currentTimeMillis()) / 1000, TimeUnit.SECONDS);
            return true;
        }
    }
    @Override
    public Boolean judgeIsVote(String openId,int voteMaxNum,long actId) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String key = VOTE_TIME_PRE + openId+String.valueOf(actId);
        if (redisTemplate.hasKey(key)) {
            int newOpenIdTime = Integer.parseInt(ops.get(key));
            if (newOpenIdTime <voteMaxNum) {
                return true;
            } else {
                return false;
            }
        } else {
            ops.set(key, 1 + "", (HandleDateUtil.getTimesnight() - System.currentTimeMillis()) / 1000, TimeUnit.SECONDS);
            return true;
        }
    }
    @Override
    public  void addVoteNum(String ip,long actId){
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String key = VOTE_TIME_PRE + ip+String.valueOf(actId);
        ops.increment(key, +1);
    }
    @Override
    public void addVoteTime(String openId,long actId) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String key = VOTE_TIME_PRE + openId+String.valueOf(actId);
        ops.increment(key, +1);
    }
    @Override
    public int getVoteNum(String openId,long actId){
        System.out.println("getvotenum"+openId);
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String key = VOTE_TIME_PRE + openId+String.valueOf(actId);
        System.out.println(redisTemplate.hasKey(key));
        if (redisTemplate.hasKey(key)) {
            int newTime = Integer.parseInt(ops.get(key));
            return newTime;
        }else {
            return 0;
        }
    }
}
