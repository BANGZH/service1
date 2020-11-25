package com.cloud.service01.domain.dto;

import lombok.Data;

/**
 * @ClassName ClinetSearchDO
 * @Description: ClinetSearchDO 类（或接口）是
 * @Author: zhonghanbang
 * @Date: 2020/11/2315:17
 */
@Data
public class ClientSearchDTO extends RequestDTO{

    private String clientName;

    private String clientType;

    private String city;

    private String agentCode;

}
