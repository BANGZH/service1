package com.cloud.service01.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName AgentClint
 * @Description: AgentClint 类（或接口）是
 * @Author: zhonghanbang
 * @Date: 2020/11/2314:19
 */
@Data
@TableName("agent_client")
public class AgentClint extends BaseEntity{
    /**
     * 系统自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "client_code")
    private String clientCode;

    @TableField(value = "client_name")
    private String clientName;

    @TableField(value = "client_gender")
    private String clientGender;

    @TableField(value = "client_phone")
    private String clientPhone;

    @TableField(value = "client_type")
    private Integer clientType;

    @TableField(value = "client_city")
    private String clientCity;

    @TableField(value = "agent_code")
    private String agentCode;

    @TableField(value = "agent_name")
    private String agentName;

}
