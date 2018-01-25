package com.binwang.frontOfBinwang.common.redis;

import com.binwang.frontOfBinwang.common.bean.httpBean.AccessToken;
import com.binwang.frontOfBinwang.common.bean.httpBean.JsApiTicket;

/**
 * Created by owen on 17/6/28.
 */
public interface CommonRAO {
    //写入手机校验码
    Boolean writeVerifyCode(String uuid, String code, int expireTime);

    //获取手机校验码
    String getVerifyCode(String uuid);

    //设置并发锁,超时时间单位毫秒
    Boolean setConcurrentLock(String key, long expireTime) throws InterruptedException;

    //删除并发锁
    void deleteConcurrentLock(String key);

    //写全局AccessToken
    Boolean writeAccessToken(AccessToken ac);

    //获取全局AccessToken key默认
    String getAccessToken();

    //写全局JsApiTicket
    Boolean writeJsApiTicket(JsApiTicket jt);

    //获取全局JsApiTicket key默认
    String getTicket();

}
