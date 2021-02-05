package com.cloud.service01.controller;

import com.cloud.service01.domain.dto.ClientSearchDTO;
import com.cloud.service01.domain.dto.ResponseDTO;
import com.cloud.service01.service.AgentClientService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName ClientController
 * @Description: ClientController 类（或接口）是
 * @Author: zhonghanbang
 * @Date: 2020/12/16 14:53
 */
@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    @Autowired
    AgentClientService agentClientService;

    @PostMapping("/author")
    @ApiOperation("获取客户列表信息")
    public ResponseDTO authorization(@RequestBody String code) {
        return ResponseDTO.builder()
                .message("获取成功")
                .data(agentClientService.getCurrentUserInfo(code))
                .build();
    }

    @GetMapping("/list")
    @ApiOperation("获取客户列表信息")
    public ResponseDTO getAgentClient(ClientSearchDTO clientSearchDTO) {
        return ResponseDTO.builder()
                .message("获取成功")
                .data(agentClientService.selectClientByCondition(clientSearchDTO))
                .build();
    }

    @GetMapping("/right")
    @ApiOperation("查看客户权益信息")
    public ResponseDTO getClientRight() {
        return ResponseDTO.builder().build();
    }



}
