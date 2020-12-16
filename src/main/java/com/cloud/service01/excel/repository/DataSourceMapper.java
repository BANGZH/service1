package com.cloud.service01.excel.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.service01.excel.domain.entity.DataSource;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataSourceMapper extends BaseMapper<DataSource> {

}
