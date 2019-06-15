package com.ssmce.dao;

import com.ssmce.domain.SysPermission;
import com.ssmce.domain.SysRole;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleDao {

    @Select("select * from sys_role")
    List<SysRole> roleAll();

    @Select("select * from sys_permission p, sys_role_permission rs where rs.roleId = #{id} and rs.permissionId=p.id")
    List<SysPermission> roleAddForPermission(Integer id);

    @Delete("delete from sys_role_permission where roleId=#{id}")
    void deleteRolePermission(Integer id);

    @Insert("insert into sys_role_permission values(#{permissionId},#{roleId})")
    void permissionAddTo(@Param("permissionId") Integer permissionId, @Param("roleId") Integer roleId);

    @Select("SELECT sr.* from sys_role sr, sys_user_role sur where sr.id=sur.roleId and sur.userId=#{id}")
    @Results(id="permissionList", value={
            @Result(id=true,property="id",column="id"),
            @Result(property="permissions",column="id", many=@Many(select = "com.ssmce.dao.SysPermissionDao.findPermissionByRoleId") )
    } )
    List<SysRole> findRoleByUserId(Integer id);

}
