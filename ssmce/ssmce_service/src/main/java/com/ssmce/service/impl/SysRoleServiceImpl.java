package com.ssmce.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssmce.dao.SysPermissionDao;
import com.ssmce.dao.SysRoleDao;
import com.ssmce.domain.SysPermission;
import com.ssmce.domain.SysRole;
import com.ssmce.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;

    @Autowired
    private SysPermissionDao sysPermissionDao;

    public PageInfo roleAll(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysRole> roles = sysRoleDao.roleAll();
        return new PageInfo<SysRole>(roles);
    }

    public List<SysRole> roleAll() {
        return sysRoleDao.roleAll();
    }

    public List<SysPermission> roleAddForPermission(Integer id) {
        return sysRoleDao.roleAddForPermission(id);
    }

    public boolean permissionAddTo(List<Integer> ids, Integer id) {
        try{
            sysRoleDao.deleteRolePermission(id);
            for (Integer toid : ids) {
                sysRoleDao.permissionAddTo(toid,id);
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
