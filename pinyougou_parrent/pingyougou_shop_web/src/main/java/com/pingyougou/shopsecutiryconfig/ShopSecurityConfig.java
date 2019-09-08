package com.pingyougou.shopsecutiryconfig;

import com.pingyougou.securitycheck.UserSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableWebSecurity
public class ShopSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserSecurityConfig userSecurityConfig;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**","/img/**","/js/**","/plugins/**","/*.html","/seller/add.shtml").permitAll()
                //设置所有的其他请求都需要认证通过即可 也就是用户名和密码正确即可不需要其他的角色
                .anyRequest().authenticated();

        //设置表单登录
        http.formLogin()
                .loginPage("/shoplogin.html")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/admin/index.html",true)
                .failureUrl("/login?error");
        http.csrf().disable();//关闭CSRF

        //开启同源iframe 可以访问策略
        http.headers().frameOptions().sameOrigin();
        //注销 并销毁session
        http.logout().logoutUrl("/logout").invalidateHttpSession(true);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityConfig).passwordEncoder(bCryptPasswordEncoder);
    }


}
