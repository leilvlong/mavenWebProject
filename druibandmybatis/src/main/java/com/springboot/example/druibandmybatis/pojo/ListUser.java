package com.springboot.example.druibandmybatis.pojo;

import java.io.Serializable;
import java.util.List;

public class ListUser implements Serializable {

    private Long id;
    private String username;
    private String password;
    private Integer age;
    private String email;
    private String sex;
    private List<ListUser> user;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<ListUser> getUser() {
        return user;
    }

    public void setUser(List<ListUser> user) {
        this.user = user;
    }
}
