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

    public ResponsePageBean getEmployeeByPage(Integer page, Integer size) {
        int start = 0;
        if (page != null && size != null) {
            start = (page - 1) * size;
        }

        List<Employee> data = employeeMapper.getEmployeeByPage(start, size);
        Long total = employeeMapper.getTotal();

        ResponsePageBean responsePageBean = new ResponsePageBean();
        responsePageBean.setData(data);
        responsePageBean.setTotal(total);
        
        return responsePageBean;
    }
}
