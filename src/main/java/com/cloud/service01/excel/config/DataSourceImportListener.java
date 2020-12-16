package com.cloud.service01.excel.config;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.cloud.service01.excel.domain.dto.JsonTempDTO;
import com.cloud.service01.excel.domain.entity.DataSource;
import com.cloud.service01.excel.domain.entity.DataSourceData;
import com.cloud.service01.excel.service.DataSourceDataService;
import com.cloud.service01.excel.service.DataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.datasource.DataSourceException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.cloud.service01.excel.domain.constant.DataSourceConstant.IMPORT_EMPLOYEE;


@Slf4j
public class DataSourceImportListener extends AnalysisEventListener<Map<Integer, String>> {

    List<DataSourceData> dataList = new ArrayList<>();
    private DataSource dataSource;
    private DataSourceService dataSourceService;
    private DataSourceDataService dataSourceDataService;
    private List<String> title;
    private String operator;
    private Integer dataSourceId;
    private LocalDateTime operatingTime;

    public DataSourceImportListener(DataSource dataSource, DataSourceService dataSourceService, DataSourceDataService dataSourceDataService) {
        this.dataSource = dataSource;
        this.dataSourceService = dataSourceService;
        this.dataSourceDataService = dataSourceDataService;
    }

    /**
     * 这里会一行行的返回头
     *
     * @param headMap
     * @param context
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        List<String> head = new ArrayList<>();
        Iterator iter = headMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            head.add((String) entry.getValue());
        }
        boolean isLegal = false;
        for (int i = 0; i < head.size() ; i++) {
            if (IMPORT_EMPLOYEE.equalsIgnoreCase(head.get(i))) {
                isLegal = true;
                break;
            }
        }
        if (!isLegal) {
            throw new DataSourceException("上传的数据文件必须包含列:" + IMPORT_EMPLOYEE + ",请检查上传的文件后重试.");
        }
        this.title = head;
        saveSource();
        log.info("do something");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        DataSourceData dataSourceData = new DataSourceData();
        List<JsonTempDTO> jsonTempDTOS = new ArrayList<>();
        String agentCode = "";
        for (int i = 0; i <title.size() ; i++) {
            if (IMPORT_EMPLOYEE.equals(title.get(i))) {
                agentCode = data.get(i) != null ? data.get(i):"";
            }
            JsonTempDTO tempDTO = new JsonTempDTO();
            tempDTO.setKey(title.get(i));
            tempDTO.setValue(data.get(i) != null ? data.get(i):"");
            jsonTempDTOS.add(tempDTO);
        }
        dataSourceData.setAgentCode(agentCode);
        dataSourceData.setDataSourceId(dataSourceId);
        dataSourceData.setJsonData(JSON.toJSONString(jsonTempDTOS));
        dataSourceData.setUpdateTime(operatingTime);
        dataSourceData.setEditor(operator);
        dataSourceData.setCreateTime(operatingTime);
        dataSourceData.setCreator(operator);
        dataList.add(dataSourceData);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成!");
        saveSourceData();
        dataSource.setRecordCount(dataList.size());
        dataSourceService.updateDataSource(dataSource);
    }

    /**
     * 保存数据源的基本信息
     */
    private void saveSource() {
        log.info("保存数据源基本信息开始!");
        this.dataSource.setDataSourceTitle(JSON.toJSONString(title));
        this.dataSource = dataSourceService.insertDataSource(dataSource);
        this.dataSourceId = dataSource.getId() ;
        this.operator =dataSource.getCreator();
        this.operatingTime = dataSource.getCreateTime();
        log.info("数据源新增基本信息成功！");
    }

    private void saveSourceData() {
        long startTime = System.currentTimeMillis();
        log.info("保存数据源数据开始!");
        dataSourceDataService.insertDataByBatch(dataList);
        log.info("数据源数据新增成功！一共耗时:{}s",(System.currentTimeMillis()-startTime)/1000);
    }

}