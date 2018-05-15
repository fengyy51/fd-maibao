package com.binwang.frontOfBinwang.vote.bean;

public class ProCommonInfo {
    private int id;//作品唯一标识
    private String content;
    private String imgUrl;
    public ProCommonInfo(){}
    public ProCommonInfo(int id,String content,String imgUrl){
        this.id=id;
        this.content=content;
        this.imgUrl=imgUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
