package com.cloud.service01.excel.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author sujl
 * @date 2019/9/10
 */
@Getter
@Setter
public class BasicEntity {

    @JsonIgnore
    @TableField("update_time")
    private LocalDateTime updateTime;

    @JsonIgnore
    private String editor;

    @JsonIgnore
    @TableField("create_time")
    private LocalDateTime createTime;

    @JsonIgnore
    private String creator;
}
