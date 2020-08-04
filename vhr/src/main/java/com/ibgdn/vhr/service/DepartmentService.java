package com.ibgdn.vhr.service;

import com.ibgdn.vhr.mapper.DepartmentMapper;
import com.ibgdn.vhr.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;

    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartmentsByParentId(-1);
    }

    /**
     存储过程：
     <p>
      DELIMITER $$

      USE `vhr`$$

      DROP PROCEDURE IF EXISTS `addDep`$$

      CREATE DEFINER=`vhr`@`%` PROCEDURE `addDep`(in depName varchar(32),in parentId int,in enabled boolean,out result int,out result2 int)

      begin
        declare did int;
        declare pDepPath varchar(64);
        insert into department set name=depName,parentId=parentId,enabled=enabled;
        select row_count() into result;
        select last_insert_id() into did;
        set result2=did;
        select depPath into pDepPath from department where id=parentId;
        update department set depPath=concat(pDepPath,'.',did) where id=did;
        update department set isParent=true where id=parentId;
      end$$

     DELIMITER;
     </p>
     */
    public void addDepartment(Department department) {
        department.setEnabled(true);
        departmentMapper.addDepartment(department);
    }
}
