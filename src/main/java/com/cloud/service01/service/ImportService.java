package com.cloud.service01.service;


import com.alibaba.excel.EasyExcel;
import com.cloud.service01.excel.config.DataSourceImportListener;
import com.cloud.service01.excel.domain.entity.DataSource;
import com.cloud.service01.excel.service.DataSourceDataService;
import com.cloud.service01.excel.service.DataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.datasource.DataSourceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @ClassName ImportService
 * @Description: ImportService 类（或接口）是
 * @Author: zhonghanbang
 * @Date: 2020/12/815:37
 */
@Slf4j
@Service
public class ImportService {

    final DataSourceDataService dataSourceDataService;

    final DataSourceService dataSourceService;

    public ImportService( DataSourceDataService dataSourceDataService, DataSourceService dataSourceService) {
        this.dataSourceDataService = dataSourceDataService;
        this.dataSourceService = dataSourceService;
    }

    @Transactional(rollbackFor = Exception.class)
    public void importData(MultipartFile importFile, String dataSourceName, String operator, Integer roleId) {
        // 校验当前用户是否有数据源导入的权限
        long startTime = System.currentTimeMillis();
        // String roleName = posterRoleService.checkUserPermission(operator,roleId,true);
        String roleName = "roleName";
        LocalDateTime now = LocalDateTime.now();
        // 保存数据源基本信息
        DataSource dataSource = new DataSource();
        dataSource.setDataSourceName(dataSourceName);
        // 先进行新增获取到数据源的id
        dataSource.setRoleId(roleId);
        dataSource.setRoleName(roleName);
        dataSource.setUpdateTime(now);
        dataSource.setEditor(operator);
        dataSource.setCreateTime(now);
        dataSource.setCreator(operator);
        // 新增数据
        try {
            EasyExcel.read(importFile.getInputStream(), new DataSourceImportListener(dataSource,dataSourceService,dataSourceDataService)).sheet().doRead();
        } catch (IOException e) {
            throw new DataSourceException("导入失败,读取上传数据文件失败，请检查文件后重试.");
        }
        log.info("一共耗时:"+(System.currentTimeMillis()-startTime)/1000);
    }


    // /**
    //  * 更新数据源以及数据
    //  */
    // @Transactional(rollbackFor = Exception.class)
    // public void updateSourceData(MultipartFile updateFile, String operator, Integer sourceId) {
    //     long startTime = System.currentTimeMillis();
    //     DataSource dataSource = dataSourceService.selectDataSourceById(sourceId);
    //     if (StringUtils.isEmpty(dataSource)) {
    //         throw new DataSourceException("找不到需要更新的数据源,请刷新后重试。");
    //     }
    //     if (!operator.equals(dataSource.getCreator())) {
    //         throw new DataSourceException("更新失败,只能更新本人创建的数据源.");
    //     }
    //     dataSource.setEditor(operator);
    //     dataSource.setUpdateTime(LocalDateTime.now());
    //     try {
    //         EasyExcel.read(updateFile.getInputStream(), new DataSourceUpdateListener(dataSource,dataSourceService,dataSourceDataService)).sheet().doRead();
    //     } catch (IOException e) {
    //         throw new DataSourceException("更新失败,读取上传数据文件失败，请检查文件后重试.");
    //     } catch (DataSourceException e2) {
    //         throw new DataSourceException(e2.getMessage());
    //     }
    //     log.info("一共耗时:"+(System.currentTimeMillis()-startTime)/1000);
    // }
}
