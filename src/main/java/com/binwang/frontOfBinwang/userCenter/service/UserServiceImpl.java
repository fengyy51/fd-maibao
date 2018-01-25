package com.binwang.frontOfBinwang.userCenter.service;

import com.binwang.frontOfBinwang.exception.ParamNotValidException;
import com.binwang.frontOfBinwang.userCenter.bean.UserInfo;
import com.binwang.frontOfBinwang.userCenter.bean.httpBean.AccessTokenInfo;
import com.binwang.frontOfBinwang.userCenter.bean.httpBean.UserInfoHttp;
import com.binwang.frontOfBinwang.userCenter.dao.IUserDAO;
import com.binwang.frontOfBinwang.userCenter.redis.UserRAO;
import com.binwang.frontOfBinwang.utils.HttpUtil;
import com.binwang.frontOfBinwang.utils.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by owen on 17/6/23.
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${wechat.appId}")
    private String APPID;

    @Value("${wechat.appSecret}")
    private String APPSECRET;

    @Resource
    private UserRAO userRAO;

    @Resource
    private IUserDAO userDAO;

    @Override
    @Transactional
    public String doAuthGetOpenId(String code) {
        if (StringUtils.isEmpty(code))
            throw new ParamNotValidException("code不能为空");
        try {
            String forAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APPID + "&secret=" + APPSECRET + "&code=" + code + "&grant_type=authorization_code";
            String accessTokenText = HttpUtil.sendGet(forAccessTokenUrl);
            AccessTokenInfo ac = JsonUtil.fromJson(accessTokenText, new TypeReference<AccessTokenInfo>() {
            });
            if (ac.getErrcode() == null) {
                String openId = ac.getOpenid();
                userDAO.addUser(openId);
                if (userRAO.writeAccessToken(ac) && userRAO.writeRefreshToken(ac)) {
                    return openId;
                } else {
                    LOGGER.error("写accessToken至Redis出错:openId为:" + openId);
                    throw new RuntimeException("出错");
                }
            } else {
                LOGGER.error("获取accessToken请求出错,code:" + code + "错误信息:" + ac.getErrmsg());
                throw new RuntimeException("出错");
            }
        } catch (RuntimeException e) {
            LOGGER.error("授权过程失败");
            throw new RuntimeException("失败");
        }
    }

    @Override
    public Map<String, Object> getInfoByOpenId(String openId) {
        try {
            Map<String, Object> reMap = new HashMap<>();
            String accessToken = userRAO.getAccessTokenByOpenId(openId);
            if (StringUtils.isEmpty(accessToken)) {
                String refreshToken = userRAO.getRefreshTokenByOpenId(openId);
                if (StringUtils.isEmpty(refreshToken)) {
                    reMap.put("doAuth", true);
                    reMap.put("userInfo", null);
                    return reMap;
                } else {
                    try {
                        accessToken = refreshAccessToken(refreshToken);
                    } catch (RuntimeException e) {
                        reMap.put("doAuth", true);
                        reMap.put("userInfo", null);
                        return reMap;
                    }
                }
            }
            String forUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN";
            String userInfoText = HttpUtil.sendGet(forUserInfoUrl);
            UserInfoHttp us = JsonUtil.fromJson(userInfoText, new TypeReference<UserInfoHttp>() {
            });
            if (us.getErrcode() == null) {
                UserInfo reUs = new UserInfo();
                reUs.setNickName(us.getNickname());
                reUs.setHeadImgUrl(us.getHeadimgurl());
                reUs.setOpenId(openId);
                reUs.setSex(us.getSex());
                reMap.put("doAuth", false);
                reMap.put("userInfo", reUs);
                return reMap;
            } else {
                LOGGER.error("拉取微信基本信息出错,openId为:" + openId);
                throw new RuntimeException("出错");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("出错");
        }
    }

    private String refreshAccessToken(String refreshToken) throws RuntimeException {
        String forRefreshAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + APPID + "&grant_type=refresh_token&refresh_token=" + refreshToken;
        String refreshAccessTokenText = HttpUtil.sendGet(forRefreshAccessTokenUrl);
        AccessTokenInfo rac = JsonUtil.fromJson(refreshAccessTokenText, new TypeReference<AccessTokenInfo>() {
        });
        if (rac.getErrcode() == null) {
            if (userRAO.writeAccessToken(rac)) {
                return rac.getAccess_token();
            } else {
                LOGGER.error("刷新accessToken至Redis出错:openId为:" + rac.getOpenid());
                throw new RuntimeException("出错");
            }
        } else {
            LOGGER.error("刷新access_token出错,errCode:" + rac.getErrcode() + "errMsg:" + rac.getErrmsg());
            throw new RuntimeException("出错");
        }
    }
}
