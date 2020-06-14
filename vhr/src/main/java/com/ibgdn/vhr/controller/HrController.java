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
}
