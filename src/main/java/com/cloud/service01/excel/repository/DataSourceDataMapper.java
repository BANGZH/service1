package com.cloud.service01.excel.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.service01.excel.domain.entity.DataSourceData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DataSourceDataMapper extends BaseMapper<DataSourceData> {

    /**
     * 更新数据源时新增数据
     * @param sourceId
     */
    int insertDataSync(@Param("sourceId") Integer sourceId);

    /**
     * 更新数据源时更新数据
     * @param sourceId
     */
    int updateDataSync(@Param("sourceId") Integer sourceId);

    /**
     * 获取数据源中的数据量总数
     * @param sourceId
     * @return
     */
    int countSourceDataRecord(@Param("sourceId") Integer sourceId);
}
