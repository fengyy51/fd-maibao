package com.binwang.frontOfBinwang.vote.service;

import com.binwang.frontOfBinwang.vote.bean.*;


import java.util.List;
import java.util.Map;

/**
 * Created by yy
 */
public interface VoteService {
    //利用openid,从redis中获取每个用户当天投票数
    int getVoteNum(long actId,String openId);
    VoteParam getVoteParam(long actId);
    long getVoteIndex(long actId);
    List<MaiBaoVoteInfo> getVoteInfo(long actId);
//    List<VoteInfo> getVoteInfo(long actId);
//    List<ProductInfo> getProductInfo(long actId);
//    麦宝修改
//    List<MaiBaoInfo> getProductInfo(long actId);
    List<ProCommonInfo> getProductInfo(long actId);
    Map<String,Object> postInfo(String str,long actId,String ip,String address,String userAgent);
    int add(long id,String str);

}

