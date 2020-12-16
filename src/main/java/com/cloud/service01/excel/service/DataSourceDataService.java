package com.cloud.service01.excel.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.service01.excel.domain.entity.DataSourceData;
import com.cloud.service01.excel.repository.DataSourceDataMapper;
import com.cloud.service01.excel.repository.DataSourceMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.datasource.DataSourceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName DataSourceService
 * @Description: DataSourceService 类（或接口）是
 * @Author: zhonghanbang
 * @Date: 2020/11/2710:20
 */
@Slf4j
@Service
public class DataSourceDataService extends ServiceImpl<DataSourceDataMapper, DataSourceData> {

    final DataSourceDataMapper dataSourceDataMapper;

    final DataSourceMapper dataSourceMapper;

    public DataSourceDataService(DataSourceDataMapper dataSourceDataMapper,  DataSourceMapper dataSourceMapper) {
        this.dataSourceDataMapper = dataSourceDataMapper;
        this.dataSourceMapper = dataSourceMapper;
     }

    /**
     * 批量新增数据源的数据
     * @param insertData
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertDataByBatch(List<DataSourceData> insertData) {
        this.saveBatch(insertData,3000);
    }

    // @Transactional(rollbackFor = Exception.class)
    // public void updateDataSync(List<DataSourceDataTemp> updateData,Integer sourceId) {
    //     // 先进行清空中间临时数据表操作
    //     dataSourceDataTempService.wipeTempSourceData();
    //     // 新增至中间表
    //     dataSourceDataTempService.saveBatch(updateData,3000);
    //     // 同步新增至正式数据表
    //     dataSourceDataMapper.insertDataSync(sourceId);
    //     // 同步更新至正式表的数据
    //     dataSourceDataMapper.updateDataSync(sourceId);
    //     // 清空中间表数据
    //     dataSourceDataTempService.wipeTempSourceData();
    // }

    /**
     * 获取指定数据源的数据
     * @return
     */
    // public PageInfo<Object> selectSourceDataByCondition(SourceDataDTO sourceDataDTO,List<String> title) {
    //     List<DataSourceData> rawData = dataSourceDataMapper.selectSourceDataByCondition(sourceDataDTO);
    //     if (rawData.isEmpty()) {
    //         return new PageInfo<>(Collections.emptyList());
    //     }
    //     List<HashMap<String,String>> result = new ArrayList<>(10);
    //
    //     for (DataSourceData data : rawData) {
    //         // 格式化Json数据
    //         List<JsonTempDTO> user = null;
    //         try {
    //             user = JSON.parseArray(data.getJsonData(),JsonTempDTO.class);
    //         }
    //         catch (Exception e) {
    //             log.warn("格式json数据出错，数据id为{}",data.getId());
    //             HashMap<String,String> personalDataMap = new HashMap<>(title.size());
    //             personalDataMap.put("id", String.valueOf(data.getId()));
    //             for (String s : title) {
    //                 personalDataMap.put(s,"");
    //             }
    //             result.add(personalDataMap);
    //         }
    //         assert user != null;
    //         if (!user.isEmpty()) {
    //             HashMap<String,String> personalRawDataMap = (HashMap<String, String>) user.stream().collect(Collectors.toMap(JsonTempDTO::getKey,JsonTempDTO::getValue));
    //             HashMap<String,String> personalDataMap = new HashMap<>(title.size());
    //             personalDataMap.put("id", String.valueOf(data.getId()));
    //             for (String s : title) {
    //                 personalDataMap.put(s,personalRawDataMap.get(s) != null ? personalRawDataMap.get(s):"");
    //             }
    //             result.add(personalDataMap);
    //         }
    //     }
    //     int total = dataSourceDataMapper.countSourceDataByCondition(sourceDataDTO);
    //     PageInfo pageInfo = new PageInfo(result);
    //     pageInfo.setTotal(total);
    //     return pageInfo;
    // }

    public Integer countSourceDataRecord(Integer sourceId) {
        return dataSourceDataMapper.countSourceDataRecord(sourceId);
    }

    /**
     * 删除指定数据
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteSourceData(Integer dataId,Integer sourceId) {
        DataSourceData data = dataSourceDataMapper.selectById(dataId);
        if (StringUtils.isEmpty(data)){
            return;
        }
        if (!data.getDataSourceId().equals(sourceId)) {
            throw new DataSourceException("不合法的删除操作,请刷新后重试。");
        }
        dataSourceDataMapper.deleteById(dataId);
    }

    /**
     * 获取用户数据
     * @param templateId
     * @return
     */
    // public Map<String, Object> selectUserDataByTemplateId(String code, Integer templateId) {
    //     PosterTemplate posterTemplate = posterTemplateMapper.selectById(templateId);
    //     if (StringUtils.isEmpty(posterTemplate)) {
    //         throw new DataSourceException("获取海报信息出错,请联系管理员[P300"+templateId+"]");
    //     }
    //     String userCode = weChatPosterService.getCurrentUserId(code);
    //     // 获取用户的数据源数据
    //     HashMap<String,Object> queryMap = new HashMap<>();
    //     queryMap.put("data_source_id",posterTemplate.getDataSourceId());
    //     queryMap.put("agent_code",userCode);
    //     List<DataSourceData> userDataList = dataSourceDataMapper.selectByMap(queryMap);
    //     if (userDataList.isEmpty()) {
    //         log.error("找不到员工对应的数据源数据,数据源{}，用户企业微信id{}",posterTemplate.getDataSourceId(),userCode);
    //         throw new DataSourceException("该海报没有您的数据，请联系管理员或重试.");
    //     }
    //
    //     // 获取数据源信息
    //     DataSource dataSource = dataSourceMapper.selectById(posterTemplate.getDataSourceId());
    //     if (StringUtils.isEmpty(dataSource)) {
    //         throw new DataSourceException("获取海报信息有误,请联系管理员[P300"+templateId+"]");
    //     }
    //     List<String> title = JSON.parseArray(dataSource.getDataSourceTitle(),String.class);
    //     // 格式化用户数据源数据
    //     DataSourceData data = userDataList.get(0);
    //     List<JsonTempDTO> user;
    //     try {
    //         user = JSON.parseArray(data.getJsonData(),JsonTempDTO.class);
    //     }
    //     catch (Exception e) {
    //         throw new DataSourceException("获取您的数据时出错，请联系管理员或重试.");
    //     }
    //     HashMap<String,String> personalDataMap = new HashMap<>(title.size());
    //     if (!StringUtils.isEmpty(user)) {
    //         HashMap<String,String> personalRawDataMap = (HashMap<String, String>) user.stream().collect(Collectors.toMap(JsonTempDTO::getKey,JsonTempDTO::getValue));
    //         personalDataMap.put("id", String.valueOf(data.getId()));
    //         for (String s : title) {
    //             personalDataMap.put(s,personalRawDataMap.get(s) != null ? personalRawDataMap.get(s):"");
    //         }
    //     }
    //     // 获取基本信息
    //     Map<String, Object> resultMap = new HashMap<>(3);
    //     List<PosterEmployeeVO> posterEmployeeVOList = employeeMapper.selectPosterUserInfo(userCode);
    //     if (posterEmployeeVOList.isEmpty()) {
    //         resultMap.put("baseInfo",new PosterEmployeeVO());
    //     }else {
    //         resultMap.put("baseInfo",posterEmployeeVOList.get(0));
    //     }
    //     resultMap.put("source",personalDataMap);
    //     return resultMap;
    // }
}
