package com.binwang.frontOfBinwang.vote.bean;

public class MaiBaoVoteInfo {
    private long actId;//活动id
    private int voteNum;
    private int itemId;//作品id
    private String name;
    private String img;
    public void MaiBaoInfo(){}
    public void MaiBaoInfo(long actId,int voteNum,int itemId,String name,String img){
        this.actId=actId;
        this.voteNum=voteNum;
        this.itemId=itemId;
        this.name=name;
        this.img=img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActId(long actId) {
        this.actId = actId;
    }

    public long getActId() {
        return actId;
    }

    public void setVoteNum(int voteNum) {
        this.voteNum = voteNum;
    }

    public int getVoteNum() {
        return voteNum;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }
}
