package com.ibgdn.vhr.controller.system.basic;

import com.ibgdn.vhr.model.Menu;
import com.ibgdn.vhr.model.Role;
import com.ibgdn.vhr.service.MenuService;
import com.ibgdn.vhr.service.RoleService;
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
    RoleService roleService;

    @Autowired
    MenuService menuService;

    /**
     * 获取所有角色信息
     *
     * @return 角色信息
     */
    @GetMapping("/")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    /**
     * 获取当前角色下的所有菜单选项
     *
     * @return 当前角色下的所有菜单选项
     */
    @GetMapping("/menus")
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }
}
