package com.ssmce.service;

import com.github.pagehelper.PageInfo;
import com.ssmce.domain.SysRole;
import com.ssmce.domain.SysUser;

import java.util.List;

public interface SysUserService {
    public SysUser findLoginUser(String username);

    PageInfo userAll(Integer pageNum, Integer pageSize);

    void userAdd(SysUser sysUser);

    List<SysRole> roleAddForAll(Integer id);

    boolean roleAddTo(List<Integer> ids, Integer userId);

    SysUser findUserById(Integer id);
}

