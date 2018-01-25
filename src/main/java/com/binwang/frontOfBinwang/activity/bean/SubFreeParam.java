package com.binwang.frontOfBinwang.activity.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yy
 */
public class SubFreeParam {
    private long id;
    private String openId;
    private long actId;
    private int chargeType ;
    private int status;
    private int isSign = 0;
    private String postReg;
    private String credentialCode;
//    private String mobile;


    public SubFreeParam() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCredentialCode() {
        return credentialCode;
    }

    public void setCredentialCode(String credentialCode) {
        this.credentialCode = credentialCode;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public long getActId() {
        return actId;
    }

    public void setActId(long actId) {
        this.actId = actId;
    }

    public String getPostReg() {
        return postReg;
    }

    public void setPostReg(String postReg) {
        this.postReg = postReg;
    }

    public int getChargeType() {
        return chargeType;
    }

    public void setChargeType(int chargeType) {
        this.chargeType = chargeType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsSign() {
        return isSign;
    }

    public void setIsSign(int isSign) {
        this.isSign = isSign;
    }

//    public String getMobile() {
//        return mobile;
//    }
//
//    public void setMobile(String mobile) {
//        this.mobile = mobile;
//    }
}
