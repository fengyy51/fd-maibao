package com.binwang.frontOfBinwang.common.service;

import com.binwang.frontOfBinwang.common.redis.CommonRAO;
import com.binwang.frontOfBinwang.exception.ParamNotValidException;
import com.binwang.frontOfBinwang.utils.SMSUtil;
import com.binwang.frontOfBinwang.utils.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;

/**
 * Created by yy on 17/12/28.
 */
@Service
public class VerifyCodeImpl implements VerifyCode {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerifyCodeImpl.class);

    @Value("${aliyun.appKey}")
    private String appKey;
    @Value("${aliyun.appSecret}")
    private String appSecret;
    @Value("${aliyun.endPoint}")
    private String endPoint;
    @Value("${aliyun.sms.signName}")
    private String signName;
    @Value("${aliyun.sms.templateCode}")
    private String templateCode;

    @Resource
    private CommonRAO commonRAO;


    @Override
    public String sendVerifyCode(String mobile) {
        try {
            //TODO:手机号校验
            if (StringUtils.isEmpty(mobile))
                throw new ParamNotValidException("手机号不合法");
            String uuid = UUIDUtil.getUUID();
            //生成随机6位数
            String code = (int) (Math.random() * 900000 + 100000) + "";
            if (SMSUtil.sendVerifyCode(appKey, appSecret, endPoint, signName, templateCode, code, mobile)) {
                if (commonRAO.writeVerifyCode(uuid, code, 60))
                    return uuid;
                else {
                    LOGGER.error("手机验证码写redis返回false,手机号为:" + mobile);
                    throw new RuntimeException("出错");
                }
            } else {
                LOGGER.error("发送短信接口返回false，手机号为:" + mobile);
                throw new RuntimeException("出错");
            }
        } catch (RuntimeException e) {
            LOGGER.error("发送短信失败");
            throw new RuntimeException("出错");
        }
    }

    @Override
    public Boolean checkVerifyCode(String uuid, String ans) {
        if (StringUtils.isEmpty(ans) || StringUtils.isEmpty(uuid))
            throw new ParamNotValidException("参数为空");
        String redisAns = commonRAO.getVerifyCode(uuid);
        if (ans.equals(redisAns))
            return true;
        else
            return false;
    }
}
