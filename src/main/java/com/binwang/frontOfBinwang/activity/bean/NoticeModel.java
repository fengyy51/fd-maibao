package com.binwang.frontOfBinwang.activity.bean;

public class NoticeModel {
    private long id;
    private String content;
    private String pubDate;
    public NoticeModel(){}
    public NoticeModel(long id,String content,String pubDate){
        this.id=id;
        this.content=content;
        this.pubDate=pubDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}
