package com.springboot.example.druibandmybatis.utils;

public class StringUtils {

    public static boolean isNotEmpty(String str){
        return str != null && str.length() > 0;
    }

    public static boolean isBlank(String str){
        return !isNotEmpty(str);
    }
}
