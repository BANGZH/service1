package com.cloud.service01.domain.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * @ClassName ClientResult
 * @Description: ClientResult 类（或接口）是
 * @Author: zhonghanbang
 * @Date: 2020/11/249:34
 */

@Data
public class ClientResult {

    private List<ClientVO> clients;

    private List<HashMap<String,String>> clientDistribution;

}
