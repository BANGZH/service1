package com.cloud.service01.excel.controller;

import com.cloud.service01.excel.domain.entity.ResponseDTO;
import com.cloud.service01.service.ImportService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName ImportController
 * @Description: ImportController 类（或接口）是
 * @Author: zhonghanbang
 * @Date: 2020/12/1615:26
 */
@RequestMapping("/api/v1/excel")
public class ImportController {

    @Autowired
    ImportService importService;

    @ApiOperation(value = "导入数据源信息", notes = "导入数据源信息")
    @PostMapping("/import")
    public ResponseDTO importClientByExcel(@RequestParam("file") MultipartFile dataFile, Integer roleId, String dataSourceName) {
        importService.importData(dataFile,dataSourceName,"userCode",roleId);
        return ResponseDTO.builder()
                .message("导入成功.")
                .build();
    }


}
