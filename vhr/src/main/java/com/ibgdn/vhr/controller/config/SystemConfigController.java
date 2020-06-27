package com.ibgdn.vhr.controller.config;

import com.ibgdn.vhr.model.Menu;
import com.ibgdn.vhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/config")
public class SystemConfigController {

    @Autowired
    MenuService menuService;

    // 不要完全相信前端传值，或许是 postman 请求或是非法请求
    @GetMapping("menu")
    public List<Menu> getMenusByHrId() {
        return menuService.getMenusByHrId();
    }
}
