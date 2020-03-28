package com.example.demo.responses;

import org.springframework.util.StringUtils;

import java.io.Serializable;

public class ErrorMsssage implements Serializable {
    private String errorMessage;



    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean hasErrorMessage(){
        return !StringUtils.isEmpty(errorMessage);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "ErrorMsssage{" +
                "errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
