package com.cloud.service01.hospital.domain.dto;

import com.cloud.service01.domain.dto.RequestDTO;
import lombok.Data;

/**
 * @ClassName HospitalSearchDTO
 * @Description: HospitalSearchDTO 类（或接口）是
 * @Author: zhonghanbang
 * @Date: 2020/12/1715:24
 */
@Data
public class HospitalSearchDTO extends RequestDTO {

    private String city;

    private String hospitalName;

}
