package com.cloud.service01.controller;

import com.cloud.service01.config.Token;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName BaseController
 * @Description: BaseController 类（或接口）是
 * @Author: zhonghanbang
 * @Date: 2020/11/139:50
 */
@RestController
@RequestMapping("/api/v1/base")
public class BaseController {

    @GetMapping
    public String getBase() {
        return "Response By Service01";
    }

    /**
     * 获取及刷新RequestToken
     */
    @PostMapping("/request")
    @Token(save = true)
    public void saveOrRefreshToken() { }

}
