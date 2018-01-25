//获取活动的自带和自定义的报名项、报名规则
package com.binwang.frontOfBinwang.activity.bean;

public class RegItemModel {
    private Long id;
    private String title;//活动名称
    private String username;
    private String regDeadLine;
    private String reg;//活动选择的自带的报名项
    private String regItem;
    private String description;//活动规则

    public RegItemModel(){}
    public RegItemModel(Long id,String reg,String regItem,String title,String regDeadLine,String username,String description){
        this.id=id;
        this.reg=reg;
        this.regItem=regItem;
        this.title=title;
        this.regDeadLine=regDeadLine;
        this.username=username;
        this.description=description;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRegItem(String regItem) {
        this.regItem = regItem;
    }

    public String getRegItem() {
        return regItem;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getReg() {
        return reg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRegDeadLine() {
        return regDeadLine;
    }

    public void setRegDeadLine(String regDeadLine) {
        this.regDeadLine = regDeadLine;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

