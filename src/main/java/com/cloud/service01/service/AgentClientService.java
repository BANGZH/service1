package com.cloud.service01.service;

import com.cloud.service01.domain.dto.ClientSearchDTO;
import com.cloud.service01.domain.entity.AgentClintTemp;
import com.cloud.service01.domain.vo.ClientVO;
import com.cloud.service01.repository.AgentClientMapper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName AgentClientService
 * @Description: AgentClientService 类（或接口）是
 * @Author: zhonghanbang
 * @Date: 2020/11/2314:31
 */
@Service
public class AgentClientService {

    final AgentClientMapper agentClientMapper;

    final AgentClientTempService agentClientTempService;

    public AgentClientService(AgentClientMapper agentClientMapper, AgentClientTempService agentClientTempService) {
        this.agentClientMapper = agentClientMapper;
        this.agentClientTempService = agentClientTempService;
    }

    /**
     * 条件查询获取符合条件的客户信息
     * @param clientSearchDTO
     * @return
     */
    public PageInfo<ClientVO> selectClientByCondition(ClientSearchDTO clientSearchDTO) {
        List<ClientVO> clientList = agentClientMapper.selectClientByCondition(clientSearchDTO);
        Integer total = agentClientMapper.countClientByCondition(clientSearchDTO);
        PageInfo<ClientVO> result = new PageInfo(clientList);
        result.setTotal(total);
        return result;
    }

    /**
     * 获取分布情况
     * @param clientSearchDTO
     */
    public List<Map<String,String>> selectClientDistributionByCondition(ClientSearchDTO clientSearchDTO) {
        List<Map<String,String>> distribution = agentClientMapper.selectClientDistributionByCondition(clientSearchDTO);
        return distribution;
    }

    /**
     * 同步客户信息
     */
    public void syncClient() {

        // todo:处理导入的客户数据
        RestTemplate restTemplate = new RestTemplate();

        List<AgentClintTemp> importClient = new ArrayList<>();
        // 新增至临时表
        agentClientTempService.saveBatch(importClient);

        // todo:同步新增至正式客户表

        // todo:同步更新至正式客户表

        // 清空临时表
        agentClientTempService.wipeClientTemp();
    }



}
