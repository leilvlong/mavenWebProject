package com.example.demo.responses;

import java.io.Serializable;

public class TypeResponse implements Serializable {

    private Integer status;

    private Object message;

    public TypeResponse() {
    }

    public TypeResponse(Integer status, Object message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
