package com.ibgdn.vhr.controller.system;

import com.ibgdn.vhr.model.Hr;
import com.ibgdn.vhr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 【系统管理】=》【操作员管理】
 */
@RestController
@RequestMapping("/system/hr")
public class HrController {

    @Autowired
    HrService hrService;

    /**
     * 获取操作员信息
     *
     * @return 操作员信息
     */
    @GetMapping("/")
    public List<Hr> getAllHrs() {
        return hrService.getAllHrs();
    }
}
