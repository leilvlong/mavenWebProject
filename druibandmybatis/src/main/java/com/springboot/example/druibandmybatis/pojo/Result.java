package com.springboot.example.druibandmybatis.pojo;

import java.io.Serializable;

public class Result implements Serializable {
    private Integer astatus;

    private String amessage;

    private Object data;

    public Integer getAstatus() {
        return astatus;
    }

    public void setAstatus(Integer astatus) {
        this.astatus = astatus;
    }

    public String getAmessage() {
        return amessage;
    }

    public void setAmessage(String amessage) {
        this.amessage = amessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
