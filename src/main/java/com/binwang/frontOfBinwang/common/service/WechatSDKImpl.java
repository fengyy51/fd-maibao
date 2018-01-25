package com.binwang.frontOfBinwang.common.service;

import com.binwang.frontOfBinwang.common.bean.httpBean.AccessToken;
import com.binwang.frontOfBinwang.common.bean.WechatSdkConfig;
import com.binwang.frontOfBinwang.common.bean.httpBean.BaseInfo;
import com.binwang.frontOfBinwang.common.bean.httpBean.JsApiTicket;
import com.binwang.frontOfBinwang.common.redis.CommonRAO;
import com.binwang.frontOfBinwang.utils.HttpUtil;
import com.binwang.frontOfBinwang.utils.JsonUtil;
import com.binwang.frontOfBinwang.utils.UUIDUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * Created by yy on 17/12/19.
 */
@Service
public class WechatSDKImpl implements WechatSDK {
    private static final Logger LOGGER = LoggerFactory.getLogger(WechatSDKImpl.class);

    @Value("${wechat.appId}")
    private String APPID;

    @Value("${wechat.appSecret}")
    private String APPSECRET;

    @Resource
    private CommonRAO commonRAO;


    @Override
    public WechatSdkConfig getConfig(String url) {
        try {
            String nonceStr = UUIDUtil.getNativeUUID();
            String timestamp = System.currentTimeMillis() + "";
            String jsapiTicket = getJsApiTicket();//。jsapi_ticket是公众号用于调用微信JS接口的临时票据 正常情况下，jsapi_ticket的有效期为7200秒，通过access_token来获
            // 注意这里参数名必须全部小写，且必须有序
            String string1 = "jsapi_ticket=" + jsapiTicket + "&noncestr=" + nonceStr
                    + "&timestamp=" + timestamp + "&url=" + url;
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            String signature = byteToHex(crypt.digest());
            WechatSdkConfig res = new WechatSdkConfig(APPID, timestamp, nonceStr, signature);
            return res;
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("出错");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException("出错");
        }
    }

    @Override
    public Boolean judgeSubscribe(String openId) {
        if (StringUtils.isEmpty(openId))
            throw new RuntimeException("参数不合法");
        try {
            String ac = commonRAO.getAccessToken();
            if (StringUtils.isEmpty(ac))
                ac = forAccessToken();
            String forIsSubScribe = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + ac + "&openid=" + openId + "&lang=zh_CN";
            String isSubScribeText = HttpUtil.sendGet(forIsSubScribe);
            BaseInfo b = JsonUtil.fromJson(isSubScribeText, new TypeReference<BaseInfo>() {
            });
            if (!StringUtils.isEmpty(b.getErrcode()))
                return false;
            if (b.getSubscribe() == 0)
                return false;
            else
                return true;
        } catch (Exception e) {
            throw new RuntimeException("出错");
        }
    }

    private String getJsApiTicket() {
        String ticket = commonRAO.getTicket();
        if (StringUtils.isEmpty(ticket)) {
            String ac = commonRAO.getAccessToken();
            try {
                if (StringUtils.isEmpty(ac))
                    ac = forAccessToken();
            } catch (Exception e) {
                throw new RuntimeException("失败");
            }
            ticket = forJsApiTicket(ac);
        }
        return ticket;
    }

    private String forJsApiTicket(String ac) {
        try {
            String forTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=" + ac;
            String ticketText = HttpUtil.sendGet(forTicketUrl);
            JsApiTicket jt = JsonUtil.fromJson(ticketText, new TypeReference<JsApiTicket>() {
            });
            if (jt.getErrcode().equals("0")) {
                if (commonRAO.writeJsApiTicket(jt)) {
                    return jt.getTicket();
                } else {
                    LOGGER.error("写全局ticket至Redis出错");
                    throw new RuntimeException("出错");
                }
            } else {
                LOGGER.error("获取全局ticket请求出错,错误信息为" + jt.getErrmsg());
                throw new RuntimeException("出错");
            }
        } catch (RuntimeException e) {
            LOGGER.error("获取全局ticket失败");
            throw new RuntimeException("失败");
        }
    }

    private String forAccessToken() {
        try {
            String forAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?appid=" + APPID + "&secret=" + APPSECRET + "&grant_type=client_credential";
            String accessTokenText = HttpUtil.sendGet(forAccessTokenUrl);
            AccessToken at = JsonUtil.fromJson(accessTokenText, new TypeReference<AccessToken>() {
            });
            if (at.getErrcode() == null) {
                if (commonRAO.writeAccessToken(at)) {
                    return at.getAccess_token();
                } else {
                    LOGGER.error("写全局accessToken至Redis出错");
                    throw new RuntimeException("出错");
                }
            } else {
                LOGGER.error("获取全局accessToken请求出错,错误信息为" + at.getErrmsg());
                throw new RuntimeException("出错");
            }
        } catch (RuntimeException e) {
            LOGGER.error("获取全局accessToken失败");
            throw new RuntimeException("失败");
        }
    }


    private String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

}
