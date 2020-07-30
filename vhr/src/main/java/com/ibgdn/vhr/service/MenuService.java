package com.ibgdn.vhr.service;

import com.ibgdn.vhr.mapper.MenuMapper;
import com.ibgdn.vhr.mapper.MenuRoleMapper;
import com.ibgdn.vhr.model.Hr;
import com.ibgdn.vhr.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    MenuRoleMapper roleMenusMapper;

    /**
     * 根据 hr 的 id 值获取对应的菜单列表
     *
     * @return 菜单列表
     */
    public List<Menu> getMenusByHrId() {
        return menuMapper.getMenusByHrId(((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }


    /**
     * 根据角色获取所有菜单列表
     *
     * @return 菜单列表
     */
//    @Cacheable todo 添加缓存
    public List<Menu> getAllMenusWithRole() {
        return menuMapper.getAllMenusWithRole();
    }

    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }

    public List<Integer> getMenuIdsByRid(Integer rid) {
        return menuMapper.getMenuIdsByRid(rid);
    }

    /**
     * 作为事务处理角色菜单更新（先删除，后插入数据）
     */
    @Transactional
    public boolean updateRoleMenus(Integer rid, Integer[] menuIds) {
        roleMenusMapper.deleteByRid(rid);
        Integer result = roleMenusMapper.insertRoleMenus(rid, menuIds);
        return result == menuIds.length;
    }

}
