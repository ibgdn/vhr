package com.ibgdn.vhr.controller.salary;

import com.ibgdn.vhr.model.ResponseBean;
import com.ibgdn.vhr.model.Salary;
import com.ibgdn.vhr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 添加工资账套信息
     *
     * @param salary 工资账套信息
     * @return ResponseBean 操作结果
     */
    @PostMapping("/")
    public ResponseBean addSalary(@RequestBody Salary salary) {
        if (salaryService.addSalary(salary) == 1) {
            return ResponseBean.ok("添加工资账套信息成功！");
        }
        return ResponseBean.error("添加工资账套信息失败！");
    }

    /**
     * 删除工资账套信息
     *
     * @param id 工资账套信息 id
     * @return ResponseBean 操作结果
     */
    @DeleteMapping("/{id}")
    public ResponseBean deleteSalary(@PathVariable Integer id) {
        if (salaryService.deleteSalary(id) == 1) {
            return ResponseBean.ok("删除工资账套信息成功！");
        }
        return ResponseBean.error("删除工资账套信息失败！");
    }

    /**
     * 更新工资账套信息
     *
     * @param salary 工资账套信息
     * @return ResponseBean 操作结果
     */
    @PutMapping("/")
    public ResponseBean updateSalary(@RequestBody Salary salary) {
        if (salaryService.updateSalary(salary) == 1) {
            return ResponseBean.ok("更新工资账套信息成功！");
        }
        return ResponseBean.error("更新工资账套信息失败！");
    }
}
