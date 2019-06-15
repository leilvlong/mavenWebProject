package com.ssmce.controllers;


import com.github.pagehelper.PageInfo;
import com.ssmce.dao.SysRoleDao;
import com.ssmce.domain.SysPermission;
import com.ssmce.service.SysPermissionService;
import com.ssmce.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @GetMapping("/all")
    public String roleAll(
            @RequestParam(name = "pageNum",required = false,defaultValue = "1")Integer pageNum,
            @RequestParam(name="pageSize",required = false,defaultValue = "2")Integer pageSize,
            Model model){
        PageInfo pageInfo = sysRoleService.roleAll(pageNum,pageSize);
        model.addAttribute("pageInfo",pageInfo);
        return "role-list.jsp";
    }

    @GetMapping("/permissionaddfor")
    public String roleAddPermission(Integer id, Model model){
        List<SysPermission> permissions = sysPermissionService.permissionAll();
        model.addAttribute("permissions",permissions);

        List<SysPermission> rolePermission = sysRoleService.roleAddForPermission(id);
        if(!rolePermission.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for (SysPermission sysPermission : rolePermission) {
                sb.append("[").append(sysPermission.getId()).append("]");
            }
            model.addAttribute("rolePermission",sb.toString());
        }
        model.addAttribute("id",id);
        return "role-permission-add.jsp";
    }

    @PostMapping("/permissionaddto")
    @ResponseBody
    public Map<String,Object> permissionAddTo(@RequestParam("ids") List<Integer> ids,@RequestParam(value = "roleId") Integer roleId){
        Map<String, Object> map = new HashMap<>();
        boolean flag = sysRoleService.permissionAddTo(ids,roleId);
        if (flag){
            map.put("message","授权成功！");
            map.put("success",true);
            return map;
        }
        map.put("message","授权失败！");
        map.put("success",false);
        return map;
    }

}
