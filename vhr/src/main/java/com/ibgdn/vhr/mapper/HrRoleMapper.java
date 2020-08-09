package com.ibgdn.vhr.mapper;

import com.ibgdn.vhr.model.HrRole;
import org.apache.ibatis.annotations.Param;

public interface HrRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HrRole record);

    int insertSelective(HrRole record);

    HrRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HrRole record);

    int updateByPrimaryKey(HrRole record);

    void deleteByHrId(Integer hrId);

    Integer addRoles(@Param("hrId") Integer hrId, @Param("rolesId") Integer[] rolesId);
}