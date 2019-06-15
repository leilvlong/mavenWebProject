package com.ssmce.dao;

import com.ssmce.domain.SysPermission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysPermissionDao {

    @Select("select * from sys_permission")
    List<SysPermission> permissionAll();

    @Insert("insert into sys_permission values (0,#{permissionName},#{url},#{pid})")
    void permissionAdd(SysPermission sysPermission);

    @Select("SELECT * from sys_permission sp, sys_role_permission srp where srp.permissionId=sp.id and srp.roleId=#{id}")
    List<SysPermission> findPermissionByRoleId(Integer id);


}

