package com.cloud.service01.controller;

import org.apache.http.entity.ContentType;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

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

}
