package com.example.demo.enums;


public enum ParameterTypes{

    String("string"),
    Integer("integer");

    private String type;

    ParameterTypes(String type) {
        this.type = type;
    }

    public java.lang.String getType() {
        return type;
    }

    public void setType(java.lang.String type) {
        this.type = type;
    }
}