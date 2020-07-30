package com.ibgdn.vhr.service;

import com.ibgdn.vhr.mapper.RoleMapper;
import com.ibgdn.vhr.model.Menu;
import com.ibgdn.vhr.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限管理 Service
 */
@Service
public class RoleService {
    @Autowired
    RoleMapper roleMapper;

    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }

    public Integer addRole(Role role) {
        if (role.getName().startsWith("ROEL_")) {
            role.setName("ROLE_" + role.getName());
        }
        return roleMapper.insert(role);
    }
}
