package com.ibgdn.vhr.controller.salary;

import com.ibgdn.vhr.model.ResponsePageBean;
import com.ibgdn.vhr.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 薪资管理 =》 员工账套设置
 */
@RestController
@RequestMapping("/salary/sobcfg")
public class SobConfigController {

    @Autowired
    EmployeeService employeeService;

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
}
