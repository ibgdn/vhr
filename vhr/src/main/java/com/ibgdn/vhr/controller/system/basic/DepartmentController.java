package com.ibgdn.vhr.controller.system.basic;

import com.ibgdn.vhr.model.Department;
import com.ibgdn.vhr.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 【系统管理】=》【基础信息设置】=》【部门管理】
 */
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    /**
     * 获取所有部门信息数据
     *
     * @return List 部门信息
     */
    @GetMapping("/")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }
}
