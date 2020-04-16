package com.example.demo.parmters;

import com.example.demo.annotations.Max;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class MessageInfo implements Serializable {

    @Max(7)
    private Integer messageType;

    private String message;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MessageInfo{" +
                "messageType=" + messageType +
                ", message='" + message + '\'' +
                '}';
    }
}
