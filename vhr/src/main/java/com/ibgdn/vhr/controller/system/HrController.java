package com.ibgdn.vhr.controller.system;

import com.ibgdn.vhr.model.Hr;
import com.ibgdn.vhr.model.ResponseBean;
import com.ibgdn.vhr.model.Role;
import com.ibgdn.vhr.service.HrService;
import com.ibgdn.vhr.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 【系统管理】=》【操作员管理】
 */
@RestController
@RequestMapping("/system/hr")
public class HrController {

    @Autowired
    HrService hrService;

    @Autowired
    RoleService roleService;

    /**
     * 获取操作员信息
     *
     * @return 操作员信息
     */
    @GetMapping("/")
    public List<Hr> getAllHrs() {
        return hrService.getAllHrs();
    }

    /**
     * 更新操作员信息
     *
     * @param hr hr 信息
     * @return 操作结果
     */
    @PutMapping("/")
    public ResponseBean updateHr(@RequestBody Hr hr) {
        if (hrService.updateHr(hr) == 1) {
            return ResponseBean.ok("更新操作员信息成功！");
        }
        return ResponseBean.error("更新操作员信息失败！");
    }

    /**
     * 获取所有用户角色
     *
     * @return 所有用户角色
     */
    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    /**
     * 修改操作员的角色
     *
     * @param hrId    操作员（hr）id
     * @param rolesId 角色（role）id
     * @return 操作员角色更新情况
     */
    @PutMapping("/hrRoles")
    public ResponseBean updateHrRoles(Integer hrId, Integer[] rolesId) {
        if (hrService.updateHrRoles(hrId, rolesId)) {
            return ResponseBean.ok("操作员角色更新成功！");
        }
        return ResponseBean.error("操作员角色更新失败！");
    }
}
