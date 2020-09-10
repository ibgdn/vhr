package com.ibgdn.vhr.service;

import com.ibgdn.vhr.mapper.EmployeeMapper;
import com.ibgdn.vhr.model.Employee;
import com.ibgdn.vhr.model.ResponsePageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 员工资料
 */
@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    DecimalFormat decimalFormat = new DecimalFormat("##.00");

    public ResponsePageBean getEmployeeByPage(Integer page, Integer size, Employee employee, Date[] beginDateScope) {
        int start = 0;
        if (page != null && size != null) {
            start = (page - 1) * size;
        }

        List<Employee> data = employeeMapper.getEmployeeByPage(start, size, employee, beginDateScope);
        Long total = employeeMapper.getTotal(employee, beginDateScope);

        ResponsePageBean responsePageBean = new ResponsePageBean();
        responsePageBean.setData(data);
        responsePageBean.setTotal(total);

        return responsePageBean;
    }

    public Integer addEmployee(Employee employee) {
        getContractTerm(employee);

        return employeeMapper.insertSelective(employee);
    }

    // 计算合同有效期
    private void getContractTerm(Employee employee) {
        // 自动计算合同有效期（单位：年）
        Date beginContract = employee.getBeginContract();
        Date endContract = employee.getEndContract();
        double monthCount = (Double.parseDouble(yearFormat.format(endContract)) - Double.parseDouble(yearFormat.format(beginContract))) * 12
                + (Double.parseDouble(monthFormat.format(endContract)) - Double.parseDouble(monthFormat.format(beginContract)));
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(monthCount / 12)));
    }

    public Integer getMaxWorkId() {
        return employeeMapper.getMaxWorkId() + 1;
    }

    public Integer deleteEmployeeById(Integer id) {
        return employeeMapper.deleteByPrimaryKey(id);
    }

    public Integer updateEmployee(Employee employee) {
        getContractTerm(employee);
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }

    public Integer addEmployees(List<Employee> employeeList) {
        return employeeMapper.addEmployees(employeeList);
    }
}
