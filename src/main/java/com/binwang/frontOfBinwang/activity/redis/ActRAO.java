package com.binwang.frontOfBinwang.activity.redis;

/**
 * Created by yy on 17/6/29.
 */
public interface ActRAO {
    //获取活动已报名人数，若没有初始化返回-1
    int getSignedNum(long actId);

    //初始活动报名人数为0
    Boolean writeSignedNum(long actId);

    //报名人数+1
    Boolean addSignedNum(long actId, int limit);

    //报名人数-1
    Boolean minusSignedNum(long actId);

}
