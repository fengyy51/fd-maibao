package com.binwang.frontOfBinwang.activity.bean;

public class ListModel {
    private long actId;
    private String img; //宣传图
    private String name;
    private String startActivityTime;
    private String endActivityTime;
    private String endSignTime;
    private String address;
    private int priceType; //0 免费 1收费

    public ListModel() {
    }

    public long getActId() {
        return actId;
    }

    public void setActId(long actId) {
        this.actId = actId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getEndSignTime() {
        return endSignTime;
    }

    public void setEndSignTime(String endSignTime) {
        this.endSignTime = endSignTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPriceType() {
        return priceType;
    }

    public void setPriceType(int priceType) {
        this.priceType = priceType;
    }
}
