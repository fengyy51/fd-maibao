package com.binwang.frontOfBinwang.luckDraw.bean;

/**
 * Created by yy on 17/7/20.
 */
public class WinCalDO {
    private long prizeId;
    private String type;
    private int ratio;
    private int num;

    public WinCalDO() {
    }

    public long getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(long prizeId) {
        this.prizeId = prizeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
