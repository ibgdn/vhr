package com.ibgdn.vhr.service;

import com.ibgdn.vhr.mapper.EmployeeMapper;
import com.ibgdn.vhr.model.Employee;
import com.ibgdn.vhr.model.ResponsePageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 员工资料
 */
@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    public ResponsePageBean getEmployeeByPage(Integer page, Integer size, String keyword) {
        int start = 0;
        if (page != null && size != null) {
            start = (page - 1) * size;
        }

        List<Employee> data = employeeMapper.getEmployeeByPage(start, size, keyword);
        Long total = employeeMapper.getTotal(keyword);

        ResponsePageBean responsePageBean = new ResponsePageBean();
        responsePageBean.setData(data);
        responsePageBean.setTotal(total);

        return responsePageBean;
    }

    public Integer addEmployee(Employee employee) {
        return employeeMapper.insertSelective(employee);
    }

    public Integer getMaxWorkId() {
        return employeeMapper.getMaxWorkId() + 1;
    }
}
