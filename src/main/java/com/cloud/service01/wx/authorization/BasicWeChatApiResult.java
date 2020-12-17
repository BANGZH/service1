package com.cloud.service01.wx.authorization;

import lombok.Getter;
import lombok.Setter;

public class BasicWeChatApiResult {
    @Getter
    @Setter
    private String errcode;
    @Getter
    @Setter
    private String errmsg;
}
