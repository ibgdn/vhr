package com.ibgdn.vhr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * hr Controller
 */
@RestController
public class HrController {
    @GetMapping("/hr")
    public String hr() {
        return "hr";
    }

    @GetMapping("/employee/basic/test")
    public String employeeBasic() {
        return "/employee/basic/test";
    }

    @GetMapping("/employee/advanced/test")
    public String employeeAdvanced() {
        return "/employee/advanced/test";
    }
}
