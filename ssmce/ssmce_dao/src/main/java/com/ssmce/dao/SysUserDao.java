package com.ssmce.dao;

import com.ssmce.domain.SysRole;
import com.ssmce.domain.SysUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface SysUserDao {

    @Select("select * from sys_user where username=#{username}")
    @ResultMap("roleList")
    public SysUser findLoginUser(String username);

    @Select("select * from sys_user")
    List<SysUser> userAll();

    @Insert("Insert into sys_user values (0,#{username},#{email},#{password},#{phoneNum},#{status})")
    void userAdd(SysUser sysUser);

    @Select("SELECT * FROM sys_role sr, sys_user_role sur where sur.userId=#{id} and sur.roleId=sr.id")
    List<SysRole> roleAddForAll(Integer id);

    @Delete("delete from sys_user_role where userId=#{userId}")
    void deleteUserRole(Integer userId);

    @Insert("insert into sys_user_role values(#{userId},#{roleId})")
    void roleAddTo(@Param("roleId") Integer roleId, @Param("userId") Integer userId);

    @Select("select * from sys_user where id=#{id}")
    @Results(id="roleList", value={
            @Result(id=true, property="id", column ="id"),
            @Result(property="roles", column="id", many=@Many(select = "com.ssmce.dao.SysRoleDao.findRoleByUserId"))
    })
    SysUser findUserById(Integer id);

}