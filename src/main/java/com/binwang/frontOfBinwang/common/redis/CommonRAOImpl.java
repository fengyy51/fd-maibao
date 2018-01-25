package com.binwang.frontOfBinwang.common.redis;

import com.binwang.frontOfBinwang.common.bean.httpBean.AccessToken;
import com.binwang.frontOfBinwang.common.bean.httpBean.JsApiTicket;
import com.binwang.frontOfBinwang.helper.RedisFather;
import org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer;
import org.apache.tomcat.jni.Thread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by owen on 17/6/28.
 */
@Repository
public class CommonRAOImpl extends RedisFather implements CommonRAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonRAOImpl.class);

    public CommonRAOImpl() {
        super.LOGGER = LOGGER;
    }

    private final String PREFIX = "fbinwang158:verifyCode";

    private final String WECHAT_TOKEN = "fbinwang158:global:accessToken";

    private final String WECHAT_TICKET = "fbinwang158:global:jsApiTicket";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public static ThreadLocal<String> threadLocalExpireTime = new ThreadLocal<>();

    @Override
    public Boolean writeVerifyCode(String uuid, String code, int expireTime) {
        String key = PREFIX + ":" + uuid;
        return super.writeStringValue(key, code, 60, "手机验证码写入redis失败");
    }

    @Override
    public String getVerifyCode(String uuid) {
        String key = PREFIX + ":" + uuid;
        return super.getStringValue(key);
    }

    @Override
    public Boolean setConcurrentLock(String key, long expireTime) throws InterruptedException {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        while (!ops.setIfAbsent(key, String.valueOf(System.currentTimeMillis() + expireTime))) {
            stringRedisTemplate.watch(key);
            Long expire = Long.parseLong(ops.get(key));
            if (expire != null && expire < System.currentTimeMillis()) {
                stringRedisTemplate.multi();
                Long oldExpire = Long.parseLong(ops.getAndSet(key, String.valueOf(System.currentTimeMillis() + expireTime)));
                if (stringRedisTemplate.exec() != null && oldExpire != null && oldExpire < System.currentTimeMillis()) {
                    break;
                }
            } else {
                stringRedisTemplate.unwatch();
            }
            TimeUnit.MILLISECONDS.sleep(3);
        }
        threadLocalExpireTime.set(ops.get(key));
        return true;
    }

    @Override
    public void deleteConcurrentLock(String key) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String redisExpire = ops.get(key);
        if (redisExpire != null && redisExpire.equals(threadLocalExpireTime.get()))
            stringRedisTemplate.delete(key);
        threadLocalExpireTime.remove();
    }

    @Override
    public Boolean writeAccessToken(AccessToken ac) {
        return super.writeStringValue(WECHAT_TOKEN, ac.getAccess_token(), ac.getExpires_in(), "写全局AccessToken失败");
    }

    @Override
    public String getAccessToken() {
        return super.getStringValue(WECHAT_TOKEN);
    }

    @Override
    public Boolean writeJsApiTicket(JsApiTicket jt) {
        return super.writeStringValue(WECHAT_TICKET, jt.getTicket(), jt.getExpires_in(), "写全局ticket失败");
    }

    @Override
    public String getTicket() {
        return super.getStringValue(WECHAT_TICKET);
    }

}
