package com.binwang.frontOfBinwang.activity.bean;

/**
 * 个人中心展示我的活动需要此model
 */
public class CredModel {
    private String name;
    private String startActivityTime;
    private String endActivityTime;
    private String address;
    private String credCode;
    private int status;
    private int sign;

    public CredModel() {
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartActivityTime() {
        return startActivityTime;
    }

    public void setStartActivityTime(String startActivityTime) {
        this.startActivityTime = startActivityTime;
    }

    public String getEndActivityTime() {
        return endActivityTime;
    }

    public void setEndActivityTime(String endActivityTime) {
        this.endActivityTime = endActivityTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCredCode() {
        return credCode;
    }

    public void setCredCode(String credCode) {
        this.credCode = credCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
