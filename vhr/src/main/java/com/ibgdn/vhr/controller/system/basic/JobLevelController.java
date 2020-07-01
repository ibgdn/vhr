package com.ibgdn.vhr.controller.system.basic;

import com.alibaba.druid.support.json.JSONUtils;
import com.ibgdn.vhr.model.JobLevel;
import com.ibgdn.vhr.model.ResponseBean;
import com.ibgdn.vhr.service.JobLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 【系统管理】=》【基础信息设置】=》【职位管理】
 */
@RestController
@RequestMapping("/system/basic/jobLevel")
public class JobLevelController {
    @Autowired
    JobLevelService jobLevelService;

    /**
     * 获取所有职称信息
     *
     * @return 所有职称数据
     */
    @GetMapping("/")
    public List<JobLevel> getAllJobLevels() {
        return jobLevelService.getAllJobLevels();
    }

    /**
     * 添加职称信息
     *
     * @param jobLevel 职称信息
     * @return ResponseBean 添加职称执行结果
     */
    @PostMapping("/")
    public ResponseBean addJobLevel(@RequestBody JobLevel jobLevel) {
        if (jobLevelService.addJobLevel(jobLevel) == 1) {
            return ResponseBean.ok("职称添加成功！");
        }
        return ResponseBean.error(JSONUtils.toJSONString(jobLevel) + " 职称添加失败！");
    }

    /**
     * 修改职称信息
     *
     * @param jobLevel 职称信息
     * @return ResponseBean 修改职称执行结果
     */
    @PutMapping("/")
    public ResponseBean updateJobLevel(@RequestBody JobLevel jobLevel) {
        if (jobLevelService.updateJobLevel(jobLevel) == 1) {
            return ResponseBean.ok("职称修改成功！");
        }
        return ResponseBean.error(JSONUtils.toJSONString(jobLevel) + " 职称修改失败！");
    }

    /**
     * 删除职称信息
     *
     * @param id 职称 id
     * @return ResponseBean 修改职称执行结果
     */
    @DeleteMapping("/{id}")
    public ResponseBean deleteJobLevel(@PathVariable Integer id) {
        if (jobLevelService.deleteJobLevel(id) == 1) {
            return ResponseBean.ok("职称删除成功！");
        }
        return ResponseBean.error(JSONUtils.toJSONString(id) + " 职称删除失败！");
    }
}
