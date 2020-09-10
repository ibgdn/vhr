package com.ibgdn.vhr.exception;

import com.ibgdn.vhr.model.ResponseBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseBean sqlException(SQLException sqlException) {
        if (sqlException instanceof SQLIntegrityConstraintViolationException) {
            // 外键约束异常处理
            return ResponseBean.error(this.getClass().getName()
                    + ": Cannot delete or update a parent row: a foreign key constraint fails!");
        }
        return ResponseBean.error(this.getClass().getName() + ": Other Sql Exception.");
    }
}
