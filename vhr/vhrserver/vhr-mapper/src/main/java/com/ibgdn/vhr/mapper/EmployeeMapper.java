package com.ibgdn.vhr.mapper;

import com.ibgdn.vhr.model.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    List<Employee> getEmployeeByPage(@Param("start") Integer start, @Param("size") Integer size,
                                     @Param("employee") Employee employee, @Param("beginDateScope") Date[] beginDateScope);

    Long getTotal(@Param("employee") Employee employee, @Param("beginDateScope") Date[] beginDateScope);

    Integer getMaxWorkId();

    Integer addEmployees(@Param("employeeList") List<Employee> employeeList);

    Employee getEmployeeById(Integer id);
}