package com.cloud.service01.excel.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName DataSource
 * @Description: DataSource 类（或接口）是数据源的数据信息
 * @Author: zhonghanbang
 * @Date: 2020/11/279:49
 */
@Data
@TableName("aia_data_source_data")
public class DataSourceData extends BasicEntity{

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("data_source_id")
    private Integer dataSourceId;

    @TableField("agent_code")
    private String agentCode;

    @TableField("json_data")
    private String jsonData;

}
