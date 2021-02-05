package com.cloud.service01.hospital.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.service01.domain.entity.BaseEntity;
import lombok.Data;

/**
 * @ClassName DataSource
 * @Description: DataSource 类（或接口）是数据源的基本信息
 * @Author: zhonghanbang
 * @Date: 2020/11/279:49
 */
@Data
@TableName("client_module_config")
public class clientModule extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("client_level")
    private String clientLevel;

    @TableField("level_name")
    private String levelName;

    @TableField("module_ids")
    private String moduleIds;

    @TableField("module_names")
    private String moduleNames;

    @TableField("module_info")
    private String moduleInfo;

}
