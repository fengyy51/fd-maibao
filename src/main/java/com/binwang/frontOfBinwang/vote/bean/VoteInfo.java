package com.binwang.frontOfBinwang.vote.bean;

/**
 * Created by yy on 2017/12/13.
 */

public class VoteInfo {
//    private int id;
    private long actId;//活动id
    private String productInfo;
    //private int id;
    private int voteNum;
//    private String productFirst;
    private int itemId;//作品id
//    private String productImgUrls;

    public VoteInfo(){
    }

    public long getActId() {
        return actId;
    }

    public void setActId(long actId) {
        this.actId = actId;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public String getProductInfo() {
        return productInfo;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getVoteNum() {
        return voteNum;
    }

    public void setVoteNum(int voteNum) {
        this.voteNum = voteNum;
    }
}
