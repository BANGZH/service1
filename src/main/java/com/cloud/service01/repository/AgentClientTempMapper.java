package com.cloud.service01.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.service01.domain.entity.AgentClint;
import com.cloud.service01.domain.entity.AgentClintTemp;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName AgentClientMapper
 * @Description: AgentClientMapper 类（或接口）是
 * @Author: zhonghanbang
 * @Date: 2020/11/2314:36
 */
@Mapper
public interface AgentClientTempMapper extends BaseMapper<AgentClintTemp> {

    void wipeClientTemp();

}
