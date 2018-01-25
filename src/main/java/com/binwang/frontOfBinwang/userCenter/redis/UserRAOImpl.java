package com.binwang.frontOfBinwang.userCenter.redis;

import com.binwang.frontOfBinwang.helper.RedisFather;
import com.binwang.frontOfBinwang.userCenter.bean.httpBean.AccessTokenInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Created by yy on 17/6/23.
 */
@Repository
public class UserRAOImpl extends RedisFather implements UserRAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRAOImpl.class);

    private final String PREFIX = "fbinwang158";

    public UserRAOImpl() {
        super.LOGGER = LOGGER;
    }


    @Override
    public Boolean writeAccessToken(AccessTokenInfo ac) {
        String key = PREFIX + ":access_token:" + ac.getOpenid();
        String value = ac.getAccess_token();
        int expire = ac.getExpires_in();
        String errMsg = "写access_token至Redis出错，openId为:" + ac.getOpenid();
        return super.writeStringValue(key, value, expire, errMsg);
    }

    @Override
    public Boolean writeRefreshToken(AccessTokenInfo ac) {
        String key = PREFIX + ":refresh_token:" + ac.getOpenid();
        String value = ac.getRefresh_token();
        String errMsg = "写refresh_token至Redis出错，openId为:" + ac.getOpenid();
        return super.writeStringValue(key, value, 60 * 60 * 24 * 30 - 10, errMsg);
    }

    @Override
    public String getAccessTokenByOpenId(String openId) {
        String key = PREFIX + ":access_token:" + openId;
        return super.getStringValue(key);
    }

    @Override
    public String getRefreshTokenByOpenId(String openId) {
        String key = PREFIX + ":refresh_token:" + openId;
        return super.getStringValue(key);
    }
}
