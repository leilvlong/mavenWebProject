package com.ssmce.service;

import com.github.pagehelper.PageInfo;
import com.ssmce.domain.SysPermission;
import com.ssmce.domain.SysRole;

import java.util.List;

public interface SysRoleService {

    PageInfo roleAll(Integer pageNum, Integer pageSize);

    List<SysRole> roleAll();

    List<SysPermission> roleAddForPermission(Integer id);

    boolean permissionAddTo(List<Integer> ids, Integer id);
}
