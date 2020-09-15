package com.ibgdn.vhr.controller.salary;

import com.ibgdn.vhr.model.Salary;
import com.ibgdn.vhr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 薪资管理 =》 工资账套服务
 */
@RestController
@RequestMapping("/salary/sob")
public class SalaryController {
    @Autowired
    SalaryService salaryService;

    /**
     * 获取所有工资信息
     *
     * @return 工资数据
     */
    @GetMapping("/")
    public List<Salary> getAllSalaries() {
        return salaryService.getAllSalaries();
    }
}
