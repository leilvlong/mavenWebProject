package com.ssmce.service.impl;

import com.ssmce.domain.SysRole;
import com.ssmce.domain.SysUser;
import com.ssmce.service.SysUserService;
import com.ssmce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private SysUserService sysUserService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser u = sysUserService.findLoginUser(username);
        List<SysRole> roles = u.getRoles();
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (SysRole role : roles) {
            String roleName = role.getRoleName();
            authorities.add(new SimpleGrantedAuthority(roleName));
        }
        User user = new User(username, u.getPassword(), authorities) ;
        return user;
    }
}





