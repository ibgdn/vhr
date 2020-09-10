package com.ibgdn.vhr.controller;

import com.ibgdn.vhr.model.ResponseBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * login controller
 */
@RestController
public class LoginController {
    @GetMapping("/login")
    public ResponseBean login() {
        return ResponseBean.error("尚未登录，请登录！");
    }
}
