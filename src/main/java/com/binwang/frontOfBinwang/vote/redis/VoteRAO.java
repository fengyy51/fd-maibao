package com.binwang.frontOfBinwang.vote.redis;

/**
 * Created by yy .
 */
public interface VoteRAO {
    Boolean judgeAuthOpenId(String openId,long actId);
    Boolean judgeIp(String ip,long actId);

    Boolean judgeIpVote(String ip,int voteMaxNum,long actId);
    Boolean judgeIsVote(String openId,int voteMaxNum,long actId);

    void addVoteNum(String ip,long actId);
    void addVoteTime(String openId,long actId);

    int getVoteNum(String openId,long actId);


}
