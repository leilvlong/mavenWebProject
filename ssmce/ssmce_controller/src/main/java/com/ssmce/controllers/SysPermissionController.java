package com.ssmce.controllers;


import com.github.pagehelper.PageInfo;
import com.ssmce.domain.SysPermission;
import com.ssmce.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/permission")
public class SysPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @GetMapping("/all")
    public String permissionAll(
            @RequestParam(name = "pageNum",required = false,defaultValue = "1")Integer pageNum,
            @RequestParam(name="pageSize",required = false,defaultValue = "2")Integer pageSize,
            Model model){
        PageInfo pageInfo = sysPermissionService.permissionAll(pageNum,pageSize);
        model.addAttribute("pageInfo",pageInfo);
        System.out.println(pageInfo);
        return "permission-list.jsp";
    }

    @PostMapping("/addto")
    public String permissionAddTo(SysPermission sysPermission){
        sysPermissionService.permissionAdd(sysPermission);
        return "redirect:/permission/all";
    }

    @GetMapping("/addfor")
    public String permissionAddFor(Model model){
        List<SysPermission> permissions = sysPermissionService.permissionAll();
        model.addAttribute("permissions",permissions);
        return "permission-add.jsp";
    }

}
