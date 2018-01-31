package com.binwang.frontOfBinwang.vote.bean;

public class MaiBaoInfo
{
    private int id;//作品唯一标识
    private String proId;//prodution id
    private String name;
    private String author;
    private String description;
    private String img;
    public MaiBaoInfo(){}
    public MaiBaoInfo(int id,String proId,String name,String author,String description,String img){
        this.proId=proId;
        this.name=name;
        this.author=author;
        this.description=description;
        this.img=img;
        this.id=id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
