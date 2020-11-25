package com.cloud.service01.domain.vo;

import lombok.Data;

/**
 * @ClassName AgentClint
 * @Description: AgentClint 类（或接口）是
 * @Author: zhonghanbang
 * @Date: 2020/11/2314:19
 */
@Data
public class ClientVO {
    /**
     * 系统自增id
     */
    private Integer id;

    private String clientCode;

    private String clientName;

    private String clientGender;

    private String clientPhone;

    private Integer clientType;

    private String clientCity;

    private String agentCode;

    private String agentName;

}
