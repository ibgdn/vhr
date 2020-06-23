package com.ibgdn.vhr.config;

import com.ibgdn.vhr.model.Menu;
import com.ibgdn.vhr.model.Role;
import com.ibgdn.vhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 根据用户请求地址，分析请求项展示时，所需要的角色。
 */
@Component
public class CustomizeFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    MenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        List<Menu> menuList = menuService.getAllMenusWithRole();
        for (Menu menu : menuList) {
            if (antPathMatcher.match(menu.getUrl(), requestUrl)) {
                List<Role> roleList = menu.getRoleList();
                String[] roleStr = new String[roleList.size()];
                for (int i = 0; i < roleStr.length; i++) {
                    roleStr[i] = roleList.get(i).getName();
                }
                return SecurityConfig.createList(roleStr);
            }
        }
        // 无法匹配的请求地址，全部要登录之后才可以访问，"ROLE_LOGIN" 只是一个没有匹配的标记。
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
