package com.binwang.frontOfBinwang.collect.service;

import com.binwang.frontOfBinwang.collect.bean.InitInfoModel;
import com.binwang.frontOfBinwang.collect.bean.PostParam;

/**
 * Created by owen on 17/7/12.
 */
public interface CollectService {
    InitInfoModel getInitInfo(int collectId);

    long postInfo(PostParam postParam);

    Boolean judgeIsPost(String openId,int collectId);

    int getIdByOpenId(String openId,int collectId);
}
