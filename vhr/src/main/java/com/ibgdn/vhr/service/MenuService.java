package com.ibgdn.vhr.service;

import com.ibgdn.vhr.mapper.MenuMapper;
import com.ibgdn.vhr.model.Hr;
import com.ibgdn.vhr.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    MenuMapper menuMapper;

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
}
