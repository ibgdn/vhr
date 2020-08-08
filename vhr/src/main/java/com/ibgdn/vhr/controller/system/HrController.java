package com.ibgdn.vhr.controller.system;

import com.ibgdn.vhr.model.Hr;
import com.ibgdn.vhr.model.ResponseBean;
import com.ibgdn.vhr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 更新操作员信息
     *
     * @param hr hr 信息
     * @return 操作结果
     */
    @PutMapping("/")
    public ResponseBean updateHr(@RequestBody Hr hr) {
        if (hrService.updateHr(hr) == 1) {
            return ResponseBean.ok("更新操作员信息成功！");
        }
        return ResponseBean.error("更新操作员信息失败！");
    }
}
