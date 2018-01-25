package com.binwang.frontOfBinwang.activity.service;

import com.binwang.frontOfBinwang.activity.bean.*;

import java.util.List;

public interface ActService {

    //TODO: 缓存需要管理系统连redis配合
//    通知
    List<NoticeModel> getNotice();
    ActModel getDetail(long actId);


    String getHtml(long actId);

    List<ListModel> getList();

    CredModel getCredDetail(String openId, long actId);

    List<ListModel> getPersonalList(String openId);

    RegItemModel getRegItem(long actId);
    List<DetailItemModel> getDetailReg(String username);
    int getSignedNum(long actId);

    Boolean isLockSign(long actId, int limit);

    Boolean isLockSignMinus(long actId);

    //添加免费活动报名,返回报名boolean
    Boolean addFree(SubFreeParam subFreeParam);

    //判断是否报名
    Boolean isSignUp(String openId, long actId);
//    取消报名
    Boolean cancelReg(long actId,String openId);

}
