package com.cloud.service01.wx.authorization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName JSTicket
 * @Description: JSTicket 类（或接口）是
 * @Author: zhonghanbang
 * @Date: 2020/12/414:58
 */
@Data
public class JSTicket {

    private String errcode;

    private String errmsg;

    private String ticket;

    @JsonProperty("expires_in")
    private String expiresIn;

    @JsonIgnore
    private LocalDateTime expiresAt;

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
        this.expiresAt = LocalDateTime.now().plusSeconds(Long.valueOf(expiresIn));
    }

}
