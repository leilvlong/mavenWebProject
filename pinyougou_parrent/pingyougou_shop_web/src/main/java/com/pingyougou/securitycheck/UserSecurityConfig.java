package com.pingyougou.securitycheck;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pingyougou.pojos.TbSeller;
import com.pingyougou.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

public class UserSecurityConfig implements UserDetailsService {

    @Reference
    private SellerService sellerService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TbSeller tb = sellerService.findOne(username);

        if (tb == null){
            return null;
        }

        if (! tb.getStatus().equals("1")){
            return null;
        }


        ArrayList<GrantedAuthority> ga = new ArrayList<>();
        ga.add(new SimpleGrantedAuthority("Admin"));
        return new User(username,tb.getPassword(), ga);
    }
}
