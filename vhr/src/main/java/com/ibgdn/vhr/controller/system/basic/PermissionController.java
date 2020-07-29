package com.ibgdn.vhr.controller.system.basic;

import com.ibgdn.vhr.model.Menu;
import com.ibgdn.vhr.model.ResponseBean;
import com.ibgdn.vhr.model.Role;
import com.ibgdn.vhr.service.MenuService;
import com.ibgdn.vhr.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 根据角色 id 获取菜单权限
     *
     * @param rid RoleId
     * @return 角色的菜单权限
     */
    @GetMapping("/menuIds/{rid}")
    public List<Integer> getMenuIdsByRid(@PathVariable Integer rid) {
        return menuService.getMenuIdsByRid(rid);
    }

    /**
     * 更新不同角色的菜单信息
     *
     * @param rid     RoleId
     * @param menuIds 菜单 id
     * @return ResponseBean 接口调用情况
     */
    @PutMapping("/")
    public ResponseBean updateRoleMenus(Integer rid, Integer[] menuIds) {
        if (menuService.updateRoleMenus(rid, menuIds)) {
            return ResponseBean.ok("根据角色 id 更新菜单权限操作成功！");
        }
        return ResponseBean.error("根据角色 id 更新菜单权限操作成功！");
    }
}
