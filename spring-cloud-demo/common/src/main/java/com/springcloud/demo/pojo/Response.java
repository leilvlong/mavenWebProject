package com.springcloud.demo.pojo;

import java.io.Serializable;

public class Response implements Serializable {

    private String message;

    private Integer status;

    public Response() {
    }

    public Response(String message, Integer status){
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
