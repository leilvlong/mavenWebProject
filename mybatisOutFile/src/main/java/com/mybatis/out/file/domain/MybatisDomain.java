package com.mybatis.out.file.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MybatisDomain implements Serializable {

    private static Map<String,String> map;

    static {
        map = new HashMap<>();
        map.put("INTEGER", "Integer");
        map.put("VARCHAR", "String");
        map.put("DOUBLE", "Double");
        map.put("TIMESTAMP", "Date");
    }

    private String columnName;

    private String columnType;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        if (columnType.contains("int")){
            columnType = "INTEGER";

        }

        else if(columnType.contains("varchar")){
            columnType = "VARCHAR";
        }

        else if (columnType.contains("double")){
            columnType = "DOUBLE";
        }

        else if(columnType.contains("datetime")){
            columnType = "TIMESTAMP";
        }

        this.columnType = columnType;
    }

    public String getJavaType(){
        return map.get(columnType);
    }

    public String getJavaField(){
        String[] split = this.columnName.split("_");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            if (i == 0){
                stringBuilder.append(split[i]);
            }else{
                stringBuilder.append(split[i].substring(0,1).toUpperCase());
                stringBuilder.append(split[i].substring(1,split[i].length()).toLowerCase());
            }
        }
        return stringBuilder.toString();
    }


    @Override
    public String toString() {
        return "MybatisDomain{" +
                "columnName='" + columnName + '\'' +
                ", columnType='" + columnType + '\'' +
                '}';
    }

    public static void main(String[] args) {

    }
}
