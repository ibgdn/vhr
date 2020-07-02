package com.ibgdn.vhr.controller.system.basic;

import com.ibgdn.vhr.model.Role;
import com.ibgdn.vhr.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 【系统管理】=》【基础信息设置】=》【权限组】
 */
@RestController
@RequestMapping("/system/basic/permission")
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    @GetMapping("/")
    public List<Role> getAllRoles() {
        return permissionService.getAllRoles();
    }
}
