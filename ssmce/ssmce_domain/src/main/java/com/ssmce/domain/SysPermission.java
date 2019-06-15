package com.ssmce.domain;

import java.util.List;

public class SysPermission {
    private Long id;
    private String permissionName;
    private String url;
    private Long pid;
    private List<SysRole> rols;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public List<SysRole> getRols() {
        return rols;
    }

    public void setRols(List<SysRole> rols) {
        this.rols = rols;
    }
}
