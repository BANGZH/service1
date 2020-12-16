package com.cloud.service01.excel.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName DataSource
 * @Description: DataSource 类（或接口）是数据源的基本信息
 * @Author: zhonghanbang
 * @Date: 2020/11/279:49
 */
@Data
@TableName("aia_data_source_info")
public class DataSource extends BasicEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("data_source_name")
    private String dataSourceName;

    @TableField("data_source_title")
    private String dataSourceTitle;

    @TableField("data_source_status")
    private Integer dataSourceStatus;

    /**
     * 数据源来源
     */
    @TableField("data_source_type")
    private Integer dataSourceType;

    @TableField("record_count")
    private Integer recordCount;

    @TableField("role_id")
    private Integer roleId;

    @TableField("role_name")
    private String roleName;

}
