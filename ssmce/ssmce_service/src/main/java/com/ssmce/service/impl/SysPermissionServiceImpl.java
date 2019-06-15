package com.ssmce.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssmce.dao.SysPermissionDao;
import com.ssmce.domain.SysPermission;
import com.ssmce.domain.SysRole;
import com.ssmce.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysPermissionDao sysPermissionDao;

    public PageInfo permissionAll(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysPermission> permissions = sysPermissionDao.permissionAll();
        return new PageInfo<SysPermission>(permissions);
    }

    public List<SysPermission> permissionAll() {
        return sysPermissionDao.permissionAll();
    }

    public void permissionAdd(SysPermission sysPermission) {
        sysPermissionDao.permissionAdd(sysPermission);
    }
}
