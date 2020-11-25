package com.cloud.service01.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @ClassName AgentClientMapper
 * @Description: AgentClientMapper 类（或接口）是
 * @Author: zhonghanbang
 * @Date: 2020/11/2314:36
 */

@Getter
@Setter
public class RequestDTO implements Serializable {

    private Integer current = 1;

    private Integer pageSize = 10;

    public Integer getStartRow() {
        if (current > 0 && pageSize > 0) {
            return (current - 1) * pageSize;
        }
        return 0;
    }
}
