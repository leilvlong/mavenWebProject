package com.travel.utils;

public class StringUtil {
    public static boolean isEmpty(String str){
        return "".equals(str) || str == null || "null".equals(str);
    }
}
