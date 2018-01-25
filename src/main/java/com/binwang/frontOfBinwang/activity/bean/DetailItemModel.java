//详细报名项要求
package com.binwang.frontOfBinwang.activity.bean;

public class DetailItemModel {
    private long id;
    private String title;
    private String dtype;
    private String ifneed;
    private String username;
    private String options;
    public DetailItemModel(){}
    public DetailItemModel(long id,String title,String dtype,String ifneed,String options,String username){
        this.id=id;
        this.title=title;
        this.dtype=dtype;
        this.ifneed=ifneed;
        this.options=options;
        this.username=username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setIfneed(String ifneed) {
        this.ifneed = ifneed;
    }

    public String getIfneed() {
        return ifneed;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }
    public void setOptions(String options) {
        this.options = options;
    }

    public String getOptions() {
        return options;
    }
}
