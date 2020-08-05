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

    /**
     * 删除部门的存储过程：
     <p>
        DELIMITER $$

        CREATE DEFINER=`vhr`@`%` PROCEDURE `deleteDep`(in did int,out result int)

        begin
            declare ecount int;
            declare pid int;
            declare pcount int;
            declare a int;
            # 获取没有子部门的部门
            select count(*) into a from department where id=did and isParent=false;
            if a=0 then set result=-2;
            else
                # 查看当前部门是否有业务员
                select count(*) into ecount from employee where departmentId=did;
                if ecount>0 then set result=-1;
                else
                    select parentId into pid from department where id=did;
                    delete from department where id=did and isParent=false;
                    select row_count() into result;
                    select count(*) into pcount from department where parentId=pid;
                    if pcount=0 then update department set isParent=false where id=pid;
                    end if;
                end if;
            end if;
        end $$

        DELIMITER ;
     </p>
     */
    public void deleteDepartmentById(Department department) {
        departmentMapper.deleteDepartmentById(department);
    }
}
