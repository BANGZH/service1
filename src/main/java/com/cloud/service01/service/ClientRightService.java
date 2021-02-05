package com.cloud.service01.service;

import com.cloud.service01.domain.entity.AgentClint;
import com.cloud.service01.hospital.domain.entity.ClientModule;
import com.cloud.service01.repository.AgentClientMapper;

/**
 * @ClassName ClientRightService
 * @Description: ClientRightService 类（或接口）是 客户权益业务服务
 * @Author: zhonghanbang
 * @Date: 2020/12/1811:56
 */
public class ClientRightService {

    final AgentClientMapper agentClientMapper;

    public ClientRightService(AgentClientMapper agentClientMapper) {
        this.agentClientMapper = agentClientMapper;
    }

    public void selectClientRightModule(Integer clientId,String operator,String action) {
        // todo:校验当前人是否是该客户的对应的营销员
        AgentClint existClient = agentClientMapper.selectById(clientId);
        Integer clientType = existClient.getClientType();
        // 获取对应客户等级的模块配置信息
        ClientModule clientModule  = new ClientModule();
        // 获取模块
        String[] moduleIds = clientModule.getModuleIds().split(",");


    }
}
