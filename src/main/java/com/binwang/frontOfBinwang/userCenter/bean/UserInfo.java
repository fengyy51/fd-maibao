package com.binwang.frontOfBinwang.userCenter.bean;

/**
 * Created by owen on 17/6/23.
 */
public class UserInfo {
    private long id;
    private String openId;
    private String nickName;
    private int sex;
    private String headImgUrl;

    public UserInfo() {
    }

    public UserInfo(long id, String openId, String nickName, int sex, String headImgUrl) {
        this.id = id;
        this.openId = openId;
        this.nickName = nickName;
        this.sex = sex;
        this.headImgUrl = headImgUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }
}
