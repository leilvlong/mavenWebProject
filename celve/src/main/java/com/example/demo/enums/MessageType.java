package com.example.demo.enums;

public enum  MessageType {

    TEXT(1,"text"),
    PICTURE(2,"picture"),
    VIDEO(3,"video");

    private Integer Type;

    private String remarks;

    MessageType(Integer type, String remarks) {
        Type = type;
        this.remarks = remarks;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
