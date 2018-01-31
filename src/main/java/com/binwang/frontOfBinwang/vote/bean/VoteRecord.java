package com.binwang.frontOfBinwang.vote.bean;

/**
 * Created by yy on 18/1/18.
 */
public class VoteRecord {
    private Long actId;
//    private String openId;
    private String ip;
    private String record;
    private String userAgent;
    private String address;//由ip获取的地址

    public VoteRecord() {
    }

    public VoteRecord(Long actId,String ip, String record, String userAgent,String address) {
        this.actId=actId;
//        this.openId=openId;
        this.ip = ip;
        this.record = record;
        this.userAgent = userAgent;
        this.address=address;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public Long getActId() {
        return actId;
    }

//    public void setOpenId(String openId) {
//        this.openId = openId;
//    }
//
//    public String getOpenId() {
//        return openId;
//    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
