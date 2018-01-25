package com.binwang.frontOfBinwang.userCenter.service;

import java.util.Map;

/**
 * Created by yy on 17/6/23.
 */
public interface UserService {
    //授权
    String doAuthGetOpenId(String code);

    //查询用户信息
    Map<String,Object> getInfoByOpenId(String openId);

}
