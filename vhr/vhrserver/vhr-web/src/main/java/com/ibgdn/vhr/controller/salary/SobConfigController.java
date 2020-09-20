package com.ibgdn.vhr.controller.salary;

import com.ibgdn.vhr.model.ResponseBean;
import com.ibgdn.vhr.model.ResponsePageBean;
import com.ibgdn.vhr.model.Salary;
import com.ibgdn.vhr.service.EmployeeService;
import com.ibgdn.vhr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 薪资管理 =》 员工账套设置
 */
@RestController
@RequestMapping("/salary/sobcfg")
public class SobConfigController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    SalaryService salaryService;

    /**
     * 分页获取员工账套信息
     *
     * @param pageIndex 页码索引
     * @param size      单页容量
     * @return ResponsePageBean 分页数据
     */
    @GetMapping("/")
    public ResponsePageBean getEmployeeByPageWithSalary(@RequestParam(defaultValue = "1") Integer pageIndex,
                                                        @RequestParam(defaultValue = "10") Integer size) {
        return employeeService.getEmployeeByPageWithSalary(pageIndex, size);
    }

    /**
     * 获取所有工资账套信息
     *
     * @return List 工资账套信息
     */
    @GetMapping("/allSalaries")
    public List<Salary> getAllSalaries() {
        return salaryService.getAllSalaries();
    }

    /**
     * 更新员工工资账套信息
     *
     * @param eid 员工 id
     * @param sid 工资账套 id
     * @return ResponseBean 操作结果
     * <p>
     * 通过 “REPLACE INTO empsalary VALUES(#{eid}, #{sid})”完成数据更新，
     * 前提需要保证 eid 唯一（数据库需要添加唯一约束），
     * 而后插入新数据只操作一次，更新数据先删除后插入操作两次，需要对所有返回值都做处理
     */
    @PutMapping("/")
    public ResponseBean updateEmployeeSalaryById(Integer eid, Integer sid) {
        Integer integer = employeeService.updateEmployeeSalaryById(eid, sid);
        if (integer == 1 || integer == 2) {
            return ResponseBean.ok("更新员工工资账套成功！");
        }
        return ResponseBean.error("更新员工工资账套失败！");
    }
}
