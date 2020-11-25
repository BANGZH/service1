package com.cloud.service01.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.service01.domain.dto.ClientSearchDTO;
import com.cloud.service01.domain.entity.AgentClint;
import com.cloud.service01.domain.vo.ClientVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @ClassName AgentClientMapper
 * @Description: AgentClientMapper 类（或接口）是
 * @Author: zhonghanbang
 * @Date: 2020/11/2314:36
 */
@Mapper
public interface AgentClientMapper extends BaseMapper<AgentClint> {

    List<ClientVO> selectClientByCondition(ClientSearchDTO clientSearchDTO);

    Integer countClientByCondition(ClientSearchDTO clientSearchDTO);

    List<Map<String,String>> selectClientDistributionByCondition(ClientSearchDTO clientSearchDTO);

}
