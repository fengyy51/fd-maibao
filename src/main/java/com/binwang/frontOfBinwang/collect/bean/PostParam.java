package com.binwang.frontOfBinwang.collect.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owen on 17/7/12.
 */
public class PostParam {
    private long id;
    private int collectId;
    private String name;
    private String wechatId;
    private String mobile;
    private String brandName;
    private String brandImgUrl = "";
    private String productImgUrls;
    private String intro;
    private String openId;
    private String recUnit;

    public PostParam() {
    }

    public String getRecUnit() {
        return recUnit;
    }

    public void setRecUnit(String recUnit) {
        this.recUnit = recUnit;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductImgUrls() {
        return productImgUrls;
    }

    public void setProductImgUrls(String productImgUrls) {
        this.productImgUrls = productImgUrls;
    }

    public int getCollectId() {
        return collectId;
    }

    public void setCollectId(int collectId) {
        this.collectId = collectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandImgUrl() {
        return brandImgUrl;
    }

    public void setBrandImgUrl(String brandImgUrl) {
        this.brandImgUrl = brandImgUrl;
    }


    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
