package com.cloud.service01.wx.authorization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author sujl
 * @date 2019/9/9
 */
public class CorpAccessToken extends BasicWeChatApiResult {
    @Getter
    @Setter
    @JsonProperty("access_token")
    private String accessToken;
    @Getter
    @JsonProperty("expires_in")
    private String expiresIn;
    @Getter
    @Setter
    @JsonIgnore
    private LocalDateTime expiresAt;

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
        this.expiresAt = LocalDateTime.now().plusSeconds(Long.valueOf(expiresIn));
    }
}
