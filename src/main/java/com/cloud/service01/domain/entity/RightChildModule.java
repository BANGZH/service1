package com.cloud.service01.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName RightModule
 * @Description: RightModule 类（或接口）是 权益的子模块信息
 * @Author: zhonghanbang
 * @Date: 2020/11/2514:18
 */
@Data
@TableName("right_child_module_info")
public class RightChildModule extends BaseEntity{
    /**
     * 系统自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "father_module_id")
    private Integer fatherModuleId;

    @TableField(value = "module_name")
    private String moduleName;

    @TableField(value = "module_info")
    private String moduleInfo;

    @TableField(value = "module_url")
    private String moduleUrl;

    @TableField(value = "module_status")
    private Integer moduleStatus;

}