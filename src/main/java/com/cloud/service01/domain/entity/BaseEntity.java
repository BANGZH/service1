package com.cloud.service01.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName BaseEntity
 * @Description: BaseInfo 类（或接口）是公共的基本属性
 * @Author: Z.hanbang
 * @Date: 2020/5/2813:50
 */
@Data
public class BaseEntity {

    @JsonIgnore
    @TableField(value = "creator", fill = FieldFill.INSERT)
    private String creator;

    @JsonIgnore
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @JsonIgnore
    @TableField(value = "editor", fill = FieldFill.INSERT_UPDATE)
    private String editor;

    @JsonIgnore
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
