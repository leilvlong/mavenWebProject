package com.ssmce.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssmce.dao.SysUserDao;
import com.ssmce.domain.SysRole;
import com.ssmce.domain.SysUser;
import com.ssmce.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public SysUser findLoginUser(String username) {
        return sysUserDao.findLoginUser(username);
    }

    public PageInfo userAll(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysUser> sysUsers = sysUserDao.userAll();
        return new PageInfo<SysUser>(sysUsers);
    }

    public void userAdd(SysUser sysUser) {
        sysUser.setPassword(bCryptPasswordEncoder.encode(sysUser.getPassword()));
        sysUserDao.userAdd(sysUser);

    }

    public List<SysRole> roleAddForAll(Integer id) {
        return sysUserDao.roleAddForAll(id);
    }

    public boolean roleAddTo(List<Integer> ids, Integer userId) {
        try{
            sysUserDao.deleteUserRole(userId);
            for (Integer toid : ids) {
                sysUserDao.roleAddTo(toid,userId);
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public SysUser findUserById(Integer id) {
        return sysUserDao.findUserById(id);
    }
}
