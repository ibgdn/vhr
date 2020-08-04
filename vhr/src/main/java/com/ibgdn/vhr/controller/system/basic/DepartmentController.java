package com.ibgdn.vhr.controller.system.basic;

import com.ibgdn.vhr.model.Department;
import com.ibgdn.vhr.model.ResponseBean;
import com.ibgdn.vhr.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 添加部门信息
     *
     * @param department 部门信息
     * @return ResponseBean 执行情况
     */
    @PostMapping("/")
    public ResponseBean addDepartment(@RequestBody Department department) {
        departmentService.addDepartment(department);
        if (department.getResult() == 1) {
            return ResponseBean.ok("部门添加成功！", department);
        }
        return ResponseBean.error("部门添加失败！", department);
    }
}
