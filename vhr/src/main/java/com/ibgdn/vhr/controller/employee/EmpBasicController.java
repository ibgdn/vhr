package com.ibgdn.vhr.controller.employee;

import com.ibgdn.vhr.model.Employee;
import com.ibgdn.vhr.model.ResponseBean;
import com.ibgdn.vhr.model.ResponsePageBean;
import com.ibgdn.vhr.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 【员工资料】=》【基本资料】
 */
@RestController
@RequestMapping("/emp/basic")
public class EmpBasicController {

    @Autowired
    EmployeeService employeeService;

    /**
     * 根据页码获取一页员工信息
     *
     * @param page    页数
     * @param size    每页展示数量
     * @param keyword 搜索关键字
     * @return 每页数据
     */
    @GetMapping("/")
    public ResponsePageBean getEmployeeByPage(@RequestParam(defaultValue = "1") Integer page,
                                              @RequestParam(defaultValue = "10") Integer size, String keyword) {
        return employeeService.getEmployeeByPage(page, size, keyword);
    }

    /**
     * 添加员工信息
     *
     * @param employee 员工信息
     * @return ResponseBean 添加员工操作情况
     */
    @PostMapping("/")
    public ResponseBean addEmployee(@RequestBody Employee employee) {
        if (employeeService.addEmployee(employee) == 1) {
            return ResponseBean.ok("添加员工成功！");
        }
        return ResponseBean.error("添加员工失败！");
    }
}
