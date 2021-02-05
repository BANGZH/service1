package com.cloud.service01.service;

import com.cloud.service01.domain.dto.ClientSearchDTO;
import com.cloud.service01.domain.entity.AgentClintTemp;
import com.cloud.service01.domain.vo.ClientVO;
import com.cloud.service01.exception.BusinessRuntimeException;
import com.cloud.service01.repository.AgentClientMapper;
import com.cloud.service01.wx.authorization.WeChatWebAuthorizationService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @ClassName AgentClientService
 * @Description: AgentClientService 类（或接口）是
 * @Author: zhonghanbang
 * @Date: 2020/11/2314:31
 */
@Slf4j
@Service
public class AgentClientService {

    final AgentClientMapper agentClientMapper;

    final AgentClientTempService agentClientTempService;

    final WeChatWebAuthorizationService weChatWebAuthorizationService;

    public AgentClientService(AgentClientMapper agentClientMapper, AgentClientTempService agentClientTempService, WeChatWebAuthorizationService weChatWebAuthorizationService) {
        this.agentClientMapper = agentClientMapper;
        this.agentClientTempService = agentClientTempService;
        this.weChatWebAuthorizationService = weChatWebAuthorizationService;
    }

    /**
     * 营销员查询获取符合条件的客户信息
     * @param clientSearchDTO
     * @return
     */
    public PageInfo<ClientVO> selectClientByCondition(ClientSearchDTO clientSearchDTO) {
        String deCodeUser = new String(Base64.getDecoder().decode(clientSearchDTO.getOperator()));
        if (StringUtils.isEmpty(clientSearchDTO.getAgentCode())) {
            log.error("营销员查询客户信息有误.没有营销员账号查询参数[{}]",clientSearchDTO.toString());
            throw new BusinessRuntimeException("操作有误,请联系管理员");
        }
        List<ClientVO> clientList = agentClientMapper.selectClientByCondition(clientSearchDTO);
        // todo:脱敏客户信息
        Integer total = agentClientMapper.countClientByCondition(clientSearchDTO);
        PageInfo<ClientVO> result = new PageInfo(clientList);
        result.setTotal(total);
        return result;
    }

    /**
     * 授权内勤查询获取符合条件的客户信息
     * @param clientSearchDTO
     * @return
     */
    public PageInfo<ClientVO> selectClientByInnerCondition(ClientSearchDTO clientSearchDTO) {
        if (StringUtils.isEmpty(clientSearchDTO.getAgentCode())) {
            log.error("营销员查询客户信息有误.没有营销员账号查询参数[{}]",clientSearchDTO.toString());
            throw new BusinessRuntimeException("操作有误,请联系管理员");
        }
        List<ClientVO> clientList = agentClientMapper.selectClientByCondition(clientSearchDTO);
        Integer total = agentClientMapper.countClientByCondition(clientSearchDTO);
        // todo:脱敏客户信息
        PageInfo<ClientVO> result = new PageInfo(clientList);
        result.setTotal(total);
        return result;
    }

    /**
     * 获取分布情况
     * @param clientSearchDTO
     */
    public List<Map<String,String>> selectClientDistributionByCondition(ClientSearchDTO clientSearchDTO) {
        // todo:区分授权内勤和外勤
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

    /**
     * 获取当前操作人的身份
     * @param code
     * @return
     */
    public Map<String, Object> getCurrentUserInfo(String code) {
        String currentUserId = weChatWebAuthorizationService.getCurrentUserId(code);
        // todo:校验用户是授权内勤还是外勤.
        Integer identity = null;
        String encodeId = Base64.getEncoder().encodeToString(currentUserId.getBytes());
        Map<String,Object> result = new HashMap<>();
        result.put("operator",  encodeId);
        result.put("identity",identity);
        return result;
    }
}
