package com.ibgdn.vhr.controller.system.basic;

import com.alibaba.druid.support.json.JSONUtils;
import com.ibgdn.vhr.model.Position;
import com.ibgdn.vhr.model.ResponseBean;
import com.ibgdn.vhr.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 【系统管理】=》【基础信息设置】=》【职位管理】
 */
@RestController
@RequestMapping("/system/basic/position")
public class PositionController {
    @Autowired
    PositionService positionService;

    /**
     * 获取所有职位
     *
     * @return 职位 List 集合
     */
    @GetMapping("/")
    public List<Position> getAllPositions() {
        return positionService.getAllPositions();
    }

    /**
     * 添加职位
     *
     * @param position 职位信息
     * @return ResponseBean 添加职位执行结果
     */
    @PostMapping("/")
    public ResponseBean addPosition(@RequestBody Position position) {
        if (positionService.addPosition(position) == 1) {
            return ResponseBean.ok("职位添加成功！");
        }
        return ResponseBean.error(JSONUtils.toJSONString(position) + " 职位添加失败！");
    }

    /**
     * 修改职位名称
     *
     * @param position 职位信息
     * @return ResponseBean 修改职位执行结果
     */
    @PutMapping("/")
    public ResponseBean updatePosition(@RequestBody Position position) {
        if (positionService.updatePosition(position) == 1) {
            return ResponseBean.ok("职位更新成功！");
        }
        return ResponseBean.error(JSONUtils.toJSONString(position) + " 职位更新失败！");
    }

    /**
     * 删除职位信息
     *
     * @param id 职位 id
     * @return ResponseBean 删除职位执行结果
     */
    @DeleteMapping("/{id}")
    public ResponseBean deletePosition(@PathVariable Integer id) {
        if (positionService.deletePosition(id) == 1) {
            return ResponseBean.ok("职位删除成功！");
        }
        return ResponseBean.error(JSONUtils.toJSONString(id) + " 职位删除失败！");
    }
}
