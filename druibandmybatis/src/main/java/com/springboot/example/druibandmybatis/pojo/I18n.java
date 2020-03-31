package com.springboot.example.druibandmybatis.pojo;

import java.io.Serializable;

public class I18n implements Serializable {

    private Integer id;

    private String textCode;

    private String zhCN;

    private String enUS;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getZhCN() {
        return zhCN;
    }

    public void setZhCN(String zhCN) {
        this.zhCN = zhCN;
    }

    public String getEnUS() {
        return enUS;
    }

    public void setEnUS(String enUS) {
        this.enUS = enUS;
    }

    public String getTextCode() {
        return textCode;
    }

    public void setTextCode(String textCode) {
        this.textCode = textCode;
    }
}
