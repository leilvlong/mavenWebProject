package com.ssmce.service;

import com.github.pagehelper.PageInfo;
import com.ssmce.domain.SysPermission;

import java.util.List;

public interface SysPermissionService {
    PageInfo permissionAll(Integer pageNum, Integer pageSize);

    List<SysPermission> permissionAll();

    void permissionAdd(SysPermission sysPermission);
}
