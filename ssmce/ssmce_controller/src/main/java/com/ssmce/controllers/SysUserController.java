package com.ssmce.controllers;

import com.github.pagehelper.PageInfo;
import com.ssmce.dao.SysUserDao;
import com.ssmce.domain.SysPermission;
import com.ssmce.domain.SysRole;
import com.ssmce.domain.SysUser;
import com.ssmce.service.SysRoleService;
import com.ssmce.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String userAll(
            @RequestParam(name = "pageNum",required = false,defaultValue = "1")Integer pageNum,
            @RequestParam(name="pageSize",required = false,defaultValue = "2")Integer pageSize,
            Model model){

        PageInfo pageInfo = sysUserService.userAll(pageNum,pageSize);
        model.addAttribute("pageInfo",pageInfo);
        return "user-list.jsp";
    }

    @PostMapping("/add")
    public String userAdd(SysUser sysUser){
        sysUserService.userAdd(sysUser);
        return "redirect:/user/all";
    }

    @GetMapping("/roleaddfor")
    public String roleAddFor(Model model,Integer id){
        List<SysRole> roles = sysUserService.roleAddForAll(id);
        if (!roles.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for (SysRole role : roles) {
                System.out.println(role.getId());
                sb.append("[").append(role.getId()).append("]");
            }
            String s = sb.toString();
            model.addAttribute("userRole",s);
        }
        List<SysRole> roleList = sysRoleService.roleAll();
        model.addAttribute("roleList",roleList);
        model.addAttribute("id",id);
        return "user-role-add.jsp";
    }

    @PostMapping("/roleaddto")
    @ResponseBody
    public Map<String,Object> roleAddTo (@RequestParam("ids")List<Integer> ids,Integer userId){
        Map<String, Object> map = new HashMap<>();
        boolean flag = sysUserService.roleAddTo(ids,userId);
        if (flag){
            map.put("message","授权成功！");
            map.put("success",true);
            return map;
        }
        map.put("message","授权失败！");
        map.put("success",false);
        return map;
    }

    @GetMapping("/find")
    public String findUserById(Integer id,Model model){

        SysUser user = sysUserService.findUserById(id);
        for (SysRole role : user.getRoles()) {
            System.out.println(role);
        }
        model.addAttribute("user",user);
        return "user-show.jsp";
    }


}
