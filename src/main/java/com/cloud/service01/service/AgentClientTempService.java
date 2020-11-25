package com.cloud.service01.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.service01.domain.entity.AgentClintTemp;
import com.cloud.service01.repository.AgentClientTempMapper;
import org.springframework.stereotype.Service;

/**
 * @ClassName AgentClientTempService
 * @Description: AgentClientTempService 类（或接口）是
 * @Author: zhonghanbang
 * @Date: 2020/11/24 9:54
 */
@Service
public class AgentClientTempService extends ServiceImpl<AgentClientTempMapper,AgentClintTemp> {

    final AgentClientTempMapper agentClientTempMapper;

    public AgentClientTempService(AgentClientTempMapper agentClientTempMapper) {
        this.agentClientTempMapper = agentClientTempMapper;
    }

    /**
     * 清空临时表
     */
    public void wipeClientTemp() {
        agentClientTempMapper.wipeClientTemp();
    }


}
