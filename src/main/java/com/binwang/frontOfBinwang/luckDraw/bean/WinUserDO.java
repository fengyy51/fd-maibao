package com.binwang.frontOfBinwang.luckDraw.bean;

/**
 * Created by yy on 17/7/20.
 */
public class WinUserDO {
    private long id;
    private String openId;
    private String actName;
    private int relationId;
    private long prizeId;
    private String code;
    private int isUse = 0;

    public WinUserDO() {
    }

    public WinUserDO(String openId, int relationId,String actName, long prizeId, String code, int isUse) {
        this.openId = openId;
        this.relationId = relationId;
        this.actName=actName;
        this.prizeId = prizeId;
        this.code = code;
        this.isUse = isUse;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getActName() {
        return actName;
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

    public int getRelationId() {
        return relationId;
    }

    public void setRelationId(int relationId) {
        this.relationId = relationId;
    }

    public long getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(long prizeId) {
        this.prizeId = prizeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getIsUse() {
        return isUse;
    }

    public void setIsUse(int isUse) {
        this.isUse = isUse;
    }
}
