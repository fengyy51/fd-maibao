package com.binwang.frontOfBinwang.userCenter.redis;

import com.binwang.frontOfBinwang.userCenter.bean.httpBean.AccessTokenInfo;

/**
 * Created by yy on 17/6/23.
 */
public interface UserRAO {
    //写accessToken -- 键 fbinwang158:access_token:openId
    Boolean writeAccessToken(AccessTokenInfo ac);

    //写refreshToken -- 键 fbinwang158:refresh_token:openId
    Boolean writeRefreshToken(AccessTokenInfo ac);

    //获取access_token by openId
    String getAccessTokenByOpenId(String openId);

    //获取refresh_token by openId
    String getRefreshTokenByOpenId(String openId);

}
