package com.cloud.service01.hospital.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.service01.domain.entity.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName DataSource
 * @Description: DataSource 类（或接口）是数据源的基本信息
 * @Author: zhonghanbang
 * @Date: 2020/11/279:49
 */
@Data
@TableName("client_hospital_config")
public class ClientHospital extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotNull
    @TableField("hospital_name")
    private String hospitalName;

    @NotNull
    @TableField("hospital_address")
    private String hospitalAddress;

    @NotNull
    @TableField("hospital_phone")
    private String hospitalPhone;

    @NotNull
    @TableField("hospital_city")
    private String hospitalCity;

}