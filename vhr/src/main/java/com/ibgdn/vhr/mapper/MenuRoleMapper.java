package com.ibgdn.vhr.mapper;

import com.ibgdn.vhr.model.MenuRole;
import org.apache.ibatis.annotations.Param;

public interface MenuRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MenuRole record);

    int insertSelective(MenuRole record);

    MenuRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MenuRole record);

    int updateByPrimaryKey(MenuRole record);

    void deleteByRid(Integer rid);

    Integer insertRoleMenus(@Param("rid") Integer rid, @Param("menuIds") Integer[] menuIds);
}