package com.ibgdn.vhr.mapper;

import com.ibgdn.vhr.model.Department;

import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    List<Department> getAllDepartmentsByParentId(Integer pid);

    void addDepartment(Department department);

    void deleteDepartmentById(Department department);

    List<Department> getAllDepartmentsWithOutChildren();
}