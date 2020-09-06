package com.ibgdn.vhr.controller.employee;

import com.ibgdn.vhr.model.*;
import com.ibgdn.vhr.service.*;
import com.ibgdn.vhr.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 【员工资料】=》【基本资料】
 */
@RestController
@RequestMapping("/emp/basic")
public class EmpBasicController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    NationService nationService;

    @Autowired
    JobLevelService jobLevelService;

    @Autowired
    PoliticsStatusService politicsStatusService;

    @Autowired
    PositionService positionService;

    @Autowired
    DepartmentService departmentService;

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

    /**
     * 获取所有民族名称
     *
     * @return 民族
     */
    @GetMapping("/nations")
    public List<Nation> getAllNations() {
        return nationService.getAllNations();
    }

    /**
     * 获取所有职位数据
     *
     * @return 职位
     */
    @GetMapping("/position")
    public List<Position> getAllPositions() {
        return positionService.getAllPositions();
    }

    /**
     * 获取所有的职称
     *
     * @return 职称
     */
    @GetMapping("/jobLevels")
    public List<JobLevel> getAllJobLevel() {
        return jobLevelService.getAllJobLevels();
    }

    /**
     * 获取所有的政治面貌选项
     *
     * @return 政治面貌
     */
    @GetMapping("/politicsStatus")
    public List<PoliticsStatus> getAllPoliticsStatus() {
        return politicsStatusService.getAllPoliticsStatus();
    }

    /**
     * 新增员工时，返回最小可用工号
     *
     * @return 最大工号
     */
    @GetMapping("/maxWorkId")
    public ResponseBean getMaxWorkId() {
        return ResponseBean.build().setStatus(200)
                .setObject(String.format("%08d", employeeService.getMaxWorkId()));
    }

    /**
     * 获取所有部门的数据
     *
     * @return 部门信息
     */
    @GetMapping("/departments")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    /**
     * 根据员工 ID 删除员工信息
     *
     * @param id 员工 ID
     * @return ResponseBean 操作结果
     */
    @DeleteMapping("/{id}")
    public ResponseBean deleteEmployeeById(@PathVariable Integer id) {
        if (employeeService.deleteEmployeeById(id) == 1) {
            return ResponseBean.ok("删除员工信息成功！");
        }
        return ResponseBean.error("删除员工信息失败！");
    }

    /**
     * 更新员工信息
     *
     * @return ResponseBean 操作结果
     */
    @PutMapping("/")
    public ResponseBean updateEmployee(@RequestBody Employee employee) {
        if (employeeService.updateEmployee(employee) == 1) {
            return ResponseBean.ok("更新员工信息成功！");
        }
        return ResponseBean.error("更新员工信息失败！");
    }

    /**
     * 导出数据（员工信息）
     *
     * @return ResponseEntity 数据
     */
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportData() {
        List<Employee> data = (List<Employee>) employeeService.getEmployeeByPage(null, null, null).getData();
        return POIUtils.employee2ExcelFile(data);
    }

    /**
     * 导入员工信息
     *
     * @param file 上传文件
     * @return ResponseBean 操作结果
     */
    @PostMapping("/import")
    public ResponseBean importData(MultipartFile file) {
        //            file.transferTo(new File("E:/cache/员工信息.xls"));
        List<Employee> employeeList = POIUtils.excelFile2Employee(file, nationService.getAllNations(),
                politicsStatusService.getAllPoliticsStatus(), departmentService.getAllDepartmentsWithOutChildren(),
                positionService.getAllPositions(), jobLevelService.getAllJobLevels());
        if (employeeService.addEmployees(employeeList) == employeeList.size()) {
            return ResponseBean.ok("导入员工信息成功！");
        }
        return ResponseBean.error("导入员工信息失败！");
    }
}
