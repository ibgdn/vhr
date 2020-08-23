package com.ibgdn.vhr.mapper;

import com.ibgdn.vhr.model.Employee;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    List<Employee> getEmployeeByPage(Integer start, Integer size, String keyword);

    Long getTotal(String keyword);

    Integer getMaxWorkId();
}