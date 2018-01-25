package com.binwang.frontOfBinwang.activity.bean;

public class ActModel {
    private long id;
    private String topImg;
    private String title;
    private int limitNumber;
    private String startActivityTime;
    private String endActivityTime;
    private String endSignTime;
    private String address;
    private int price;
    private int faceObj;
    private String username;
    public ActModel() {
    }

    public ActModel(long id, String topImg, String title, int limitNumber, String startActivityTime, String endActivityTime, String endSignTime, String address, int price,int faceObj,String username) {
        this.id = id;
        this.topImg = topImg;
        this.title = title;
        this.limitNumber = limitNumber;
        this.startActivityTime = startActivityTime;
        this.endActivityTime = endActivityTime;
        this.endSignTime = endSignTime;
        this.address = address;
        this.price = price;
        this.faceObj=faceObj;
        this.username=username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTopImg() {
        return topImg;
    }

    public void setTopImg(String topImg) {
        this.topImg = topImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLimitNumber() {
        return limitNumber;
    }

    public void setLimitNumber(int limitNumber) {
        this.limitNumber = limitNumber;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getFaceObj() {
        return faceObj;
    }

    public void setFaceObj(int faceObj) {
        this.faceObj = faceObj;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
