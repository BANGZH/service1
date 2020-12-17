package com.cloud.service01.wx.authorization;

import org.apache.ibatis.datasource.DataSourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @ClassName JSTicketService
 * @Description: JSTicketService 类（或接口）是
 * @Author: zhonghanbang
 * @Date: 2020/12/414:55
 */
@Service
public class JSTicketService {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Do any additional configuration here
        return builder.build();
    }

    @Autowired
    private RestTemplate restTemplate;

    @Value("${weixin.get-js-ticket-api}")
    private String apiUrl;

    private JSTicket jsTicket;

    private Long refreshFlag = 10L;

    public JSTicket getTicket(String accessToken) {
        if (null == jsTicket || isTicketExpire()) {
            //距离超时时间不足10秒，则刷新一次token
            synchronized (this) {
                if (null == jsTicket || isTicketExpire()) {
                    jsTicket = getTicketByHttp(accessToken);
                }
            }
        }
        return jsTicket;
    }

    private boolean isTicketExpire() {
        if (null == jsTicket.getExpiresAt()) {
            return false;
        }
        return LocalDateTime.now().until(jsTicket.getExpiresAt(), ChronoUnit.SECONDS) < refreshFlag;
    }

    private JSTicket getTicketByHttp(String accessToken) {
        String formatUrl = MessageFormat.format(apiUrl,accessToken);
        JSTicket jsTicket = restTemplate.getForObject(formatUrl, JSTicket.class);
        if (!"0".equals(jsTicket.getErrcode())) {
            throw new DataSourceException("获取ticket出错，请联系管理员,错误码[" + jsTicket.getErrcode() + "]");
        }
        return jsTicket;
    }



}
