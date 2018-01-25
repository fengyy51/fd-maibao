package com.binwang.frontOfBinwang.common.bean.httpBean;

/**
 * Created by owen on 17/8/16.
 */
public class BaseInfo {
    private int subscribe;
    private String openid;
    private String errcode;
    private String errmsg;

    public BaseInfo() {
    }

    public int getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(int subscribe) {
        this.subscribe = subscribe;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
