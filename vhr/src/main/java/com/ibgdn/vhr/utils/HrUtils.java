package com.ibgdn.vhr.utils;

import com.ibgdn.vhr.model.Hr;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 操作员（Hr）工具类
 */
public class HrUtils {
    /**
     * 获取当前操作员（Hr）对象
     *
     * @return 操作员（Hr）
     */
    public static Hr getCurrentHr() {
        return (Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
