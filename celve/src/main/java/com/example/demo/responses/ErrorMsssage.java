package com.example.demo.responses;

import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ErrorMsssage implements Serializable {
    private  List<String> errorMessage;



    public void setErrorMessage(List<String> errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean hasErrorMessage(){
        return !errorMessage.isEmpty();
    }

    public List<String> getErrorMessage() {
        return this.errorMessage;
    }

    @Override
    public String toString() {
        return "ErrorMsssage{" +
                "errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
