package com.cloud.service01.hospital.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.service01.exception.BusinessRuntimeException;
import com.cloud.service01.hospital.domain.dto.HospitalSearchDTO;
import com.cloud.service01.hospital.domain.entity.ClientHospital;
import com.cloud.service01.hospital.repository.ClientHospitalMapper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @ClassName ClientHospitalService
 * @Description: ClientHospitalService 类（或接口）是
 * @Author: zhonghanbang
 * @Date: 2020/12/1715:06
 */
@Service
public class ClientHospitalService extends ServiceImpl<ClientHospitalMapper, ClientHospital> {

    final ClientHospitalMapper clientHospitalMapper;

    public ClientHospitalService(ClientHospitalMapper clientHospitalMapper) {
        this.clientHospitalMapper = clientHospitalMapper;
    }

    /**
     * 新增医院配置信息
     * @return
     */
    public void insertHospital(ClientHospital clientHospital) {
        clientHospitalMapper.insert(clientHospital);
    }

    /**
     * 获取医院配置信息列表
     * @param hospitalSearchDTO
     * @return
     */
    public PageInfo<ClientHospital> selectHospital(HospitalSearchDTO hospitalSearchDTO) {
        return new PageInfo<>(Collections.emptyList());
    }

    /**
     * 删除医院配置
     * @param id
     */
    public void deleteHospital(String id) {
        clientHospitalMapper.deleteById(id);
    }

    /**
     * 更新医院配置信息
     */
    public void updateHospital(ClientHospital clientHospital) {
        if (StringUtils.isEmpty(clientHospital.getId())) {
            throw new BusinessRuntimeException("请先选择.");
        }
        clientHospitalMapper.updateById(clientHospital);
    }

    /**
     * 获取指定城市的体检医院信息
     * @param city
     * @return
     */
    public List<ClientHospital> SelectHospitalByCity(String city) {
        Map<String,Object> queryCondition = new HashMap<>(1);
        queryCondition.put("hospital_city",city);
        return clientHospitalMapper.selectByMap(queryCondition);
    }

    public Map<String, List> SelectHospitalCity() {
        // todo:获取城市
        List<String> cityList = new ArrayList<>();
        Map<String, List> result = new HashMap<>(1);
        result.put("hospitalCity",cityList);
        return  result;
    }

}