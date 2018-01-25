package com.binwang.frontOfBinwang.luckDraw.bean;

/**
 * Created by yy on 17/7/20.
 */
public class WinModel {
    private long id;
    private long prizeId;
    private long relationId;
    private String name;
    private String info;
    private String type;
    private String duijiangTime;
    private String duijiangLoc;
    private String code;
    private int isUse;

    public WinModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIsUse() {
        return isUse;
    }

    public void setIsUse(int isUse) {
        this.isUse = isUse;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(long prizeId) {
        this.prizeId = prizeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuijiangTime() {
        return duijiangTime;
    }

    public void setDuijiangTime(String duijiangTime) {
        this.duijiangTime = duijiangTime;
    }

    public String getDuijiangLoc() {
        return duijiangLoc;
    }

    public void setDuijiangLoc(String duijiangLoc) {
        this.duijiangLoc = duijiangLoc;
    }

    public void setRelationId(long relationId) {
        this.relationId = relationId;
    }

    public long getRelationId() {
        return relationId;
    }
}
