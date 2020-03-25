package com.example.demo.parmters;

import java.io.Serializable;

public class MessageInfo implements Serializable {

    private Integer messageType;

    private String message;

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
