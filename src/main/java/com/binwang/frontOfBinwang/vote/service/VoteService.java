package com.binwang.frontOfBinwang.vote.service;

import com.binwang.frontOfBinwang.vote.bean.VoteInfo;
import com.binwang.frontOfBinwang.vote.bean.ProductInfo;
import com.binwang.frontOfBinwang.vote.bean.VoteParam;


import java.util.List;
import java.util.Map;

/**
 * Created by yy
 */
public interface VoteService {
    //获取每个用户当天投票数
    int getVoteNum(long actId,String openId);
    VoteParam getVoteParam(long actId);
    List<VoteInfo> getVoteInfo(long actId);
    List<ProductInfo> getProductInfo(long actId);
    Map<String,Object> postInfo(String str,long actId,String ip,String userAgent);
    int add(long id,String str);

}

