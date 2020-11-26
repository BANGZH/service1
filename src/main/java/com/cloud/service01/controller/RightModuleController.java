package com.cloud.service01.controller;

import com.cloud.service01.config.Token;
import com.cloud.service01.service.RightModuleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RightModuleConroller
 * @Description: RightModuleConroller 类（或接口）是
 * @Author: zhonghanbang
 * @Date: 2020/11/2515:49
 */
@RestController
@RequestMapping("/api/v1/module")
public class RightModuleController {

    final RightModuleService rightModuleService;

    public RightModuleController(RightModuleService rightModuleService) {
        this.rightModuleService = rightModuleService;
    }

    @PostMapping("/delete")
    public void deleteModule(Integer moduleId) {
        rightModuleService.deleteModule(moduleId,"ddd");
    }

    @GetMapping("1")
    @Token(check = true,save = true)
    public void test1() {

    }

    @GetMapping("2")
    @Token(save = true)
    public void test2() {

    }

    @GetMapping("3")
    @Token(check = true)
    public void test3() {

    }

}
