package com.cloud.service01.controller;

import com.cloud.service01.config.Token;
import com.cloud.service01.domain.entity.RightChildModule;
import com.cloud.service01.domain.entity.RightModule;
import com.cloud.service01.service.RightModuleService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public void insertModule(@RequestBody RightModule rightModule) {
        rightModuleService.addModule(rightModule,"dddd");
    }

    @PostMapping("/update")
    public void updateModule(@RequestBody RightModule rightModule) {
        rightModuleService.updateModule(rightModule,"dddd");
    }

    @PostMapping("/child/add")
    public void insertChildModule(@RequestBody RightChildModule rightModule) {
        rightModuleService.addChildModule(rightModule,"dddd");
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
