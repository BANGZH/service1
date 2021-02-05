package com.cloud.service01.wx.authorization;

import com.alibaba.fastjson.JSONObject;
import com.cloud.service01.exception.BusinessRuntimeException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.datasource.DataSourceException;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.UUID;

/**
 * @ClassName WechatService
 * @Description: WechatService 类（或接口）是 构建网页授权链接和获取用户的身份
 * @Author: zhonghanbang
 * @Date: 2020/12/314:56
 */
@Service
public class WeChatPosterService {

    /**
     * 网页授权api
     */
    @Value("${weixin.web-authorize-url}")
    private String authPreUrl;

    /**
     * 海报跳转地址
     */
    @Value("${weixin.poster-address}")
    private String posterAddress;

    /**
     * 企业id
     */
    @Value("${weixin.work.corpid}")
    private String corpId;

    @Value("${weixin.get-user-info-api}")
    private String getUerInfoAPI;

    /**
     * 海报应用secret
     */
    @Value("${weixin.posterapp.secret}")
    private String agentSecret;

    @Value("${weixin.work.api-prefix}")
    private String apiPrefix;

    @Value("${weixin.work.api.token}")
    private String apiToken;

    @Autowired
    private RestTemplate client;

    @Autowired
    private JSTicketService jsTicketService;

    /**
     * 构建网页授权链接
     * @return
     */
    public String buildAuthorizeUrl(Integer templateId) throws UnsupportedEncodingException {
        String encodeId = Base64Util.encode(String.valueOf(templateId));
        String redirectUrl = posterAddress + "?templateId=";
        URLEncoder.encode(redirectUrl,"UTF-8");
        String formatUrl = MessageFormat.format(authPreUrl,corpId,URLEncoder.encode(redirectUrl,"UTF-8")+encodeId);
        System.out.println(formatUrl);
        return formatUrl;
    }

    /**
     * 网页授权链接获取用户身份
     * @param code
     * @return
     */
    public String getCurrentUserId(String code) {
        CorpAccessToken corpAccessToken = getAccessTokenByHttp();
        String formatUrl = MessageFormat.format(getUerInfoAPI,corpAccessToken.getAccessToken(),code);
        String result = client.getForObject(formatUrl, String.class);
        HashMap<String,Object> resultMap = JSONObject.parseObject(result,HashMap.class);
        if (resultMap.containsKey("errcode")) {
            if (resultMap.get("errcode").toString().equals("0")) {
                if (resultMap.containsKey("UserId")) {
                    return resultMap.get("UserId").toString();
                }
                throw new DataSourceException("获取您的身份信息有误,您不是该企业成员.");
            }else {
                throw new DataSourceException("获取您的身份信息有误,请联系管理员，错误码" + resultMap.get("errcode"));
            }
        }
        throw new DataSourceException("获取您的身份信息有误,请联系管理员");
    }

    /**
     * 获取海报应用的access_token
     * @return
     */
    private CorpAccessToken getAccessTokenByHttp() {
        String url = UriComponentsBuilder.fromHttpUrl(apiPrefix + apiToken)
                .queryParam("corpid", corpId)
                .queryParam("corpsecret", agentSecret).build().encode().toString();
        CorpAccessToken newAccessToken = client.getForObject( url, CorpAccessToken.class);
        if (!"0".equals(newAccessToken.getErrcode())) {
            throw new BusinessRuntimeException(newAccessToken.getErrcode(), newAccessToken.getErrmsg());
        }
        return newAccessToken;
    }

    /**
     * 提供加密串签名信息
     * @return
     */
    public HashMap<String, Object> getSignature(String url) {
        Long timestamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        String nonceStr = UUID.randomUUID().toString();
        CorpAccessToken corpAccessToken = getAccessTokenByHttp();
        // 获取js-ticket
        JSTicket jsTicket = jsTicketService.getTicket(corpAccessToken.getAccessToken());
        // 拼接加密串
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("jsapi_ticket=")
                .append(jsTicket.getTicket())
                .append("&noncestr=")
                .append(nonceStr)
                .append("&timestamp=")
                .append(timestamp)
                .append("&url=")
                .append(url);
        HashMap<String,Object> resultMap = new HashMap<>();
        resultMap.put("appId",corpId);
        resultMap.put("timestamp",timestamp);
        resultMap.put("nonceStr",nonceStr);
        resultMap.put("signature", DigestUtils.sha1Hex(stringBuilder.toString()));
        return resultMap;
    }
}
