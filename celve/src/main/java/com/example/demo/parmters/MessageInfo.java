package com.example.demo.parmters;

import com.example.demo.annotations.Max;

import java.io.Serializable;

public class MessageInfo implements Serializable {

    @Max(7)
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

    @Override
    public String toString() {
        return "MessageInfo{" +
                "messageType=" + messageType +
                ", message='" + message + '\'' +
                '}';
    }
}
