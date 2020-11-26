package com.cloud.service01.config;

import com.cloud.service01.exception.BusinessRuntimeException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("unchecked")
public class TokenInterceptor extends HandlerInterceptorAdapter {

    private static final String REQUEST_TOKEN = "requestToken";

    private static final String TOKEN_MAP = "tokenMap";

    /**
     * 使用Token控制重复提交方法
     * 请求预处理方法
     * 对于标注@Token(save=true)的方法自动生成或刷新token
     * 对于标注@Token(check=true)的方法校验客户端的token是否在session中存在,如果不存在抛异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            //只有设有注解的方法才处理
            Token annotation = method.getAnnotation(Token.class);
            if (annotation != null) {
                boolean needCheck = annotation.check();
                if (needCheck) {
                    // 校验令牌
                    checkToken(request);
                }
                boolean needSave = annotation.save();
                if (needSave) {
                    // 保存或刷新令牌
                    saveOrRefreshToken(request);
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    /**
     * 生成或刷新Token,Token使用uuid生成.
     */
    private void saveOrRefreshToken(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object tokenObj = session.getAttribute(TOKEN_MAP);
        HashMap<String, String> tokenMap = null;
        if (tokenObj == null) {
            // 如果tokenMap为空
            tokenMap = new HashMap<>();
            tokenMap.put(REQUEST_TOKEN, UUID.randomUUID().toString());
            session.setAttribute(TOKEN_MAP, tokenMap);
        } else if (tokenObj instanceof Map) {
            // 如果tokenMap已经存在,就直接覆盖更新
            tokenMap = (HashMap<String, String>) tokenObj;
            tokenMap.put(REQUEST_TOKEN, UUID.randomUUID().toString());
        }
        if (tokenMap != null) {
            request.setAttribute(REQUEST_TOKEN, tokenMap.get(REQUEST_TOKEN));
        }
    }

    /**
     * Token校验,比对客户端传过来的token是否在session中存在.
     */
    private void checkToken(HttpServletRequest request) {
        // 判断客户端传过来的token是否在session的tokenMap中存在,存在则移除,不存在就是重复提交
        String tokenKey = null;
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new BusinessRuntimeException("当前页面操作已超时,请刷新后重试.");
        }
        HashMap<String, String> tokenMap = (HashMap<String, String>) session.getAttribute(TOKEN_MAP);
        if (tokenMap != null && !tokenMap.isEmpty()) {
            String clientToken = request.getParameter(REQUEST_TOKEN);
            if (clientToken != null) {
                for (Map.Entry<String, String> entry : tokenMap.entrySet()) {
                    if (clientToken.equals(entry.getValue())) {
                        tokenKey = entry.getKey();
                        // 校验成功后刷新请求token
                        tokenMap.put(tokenKey, UUID.randomUUID().toString());
                        session.setAttribute(TOKEN_MAP, tokenMap);
                        request.setAttribute(REQUEST_TOKEN, tokenMap.get(tokenKey));
                        break;
                    } else {
                        throw new BusinessRuntimeException("当前页面已过期,请刷新页面后再试！");
                    }
                }
            }
        }
        if (tokenKey == null) {
            throw new BusinessRuntimeException("当前页面已过期，请刷新页面后再试！或者您也可以在最后打开的页面中操作！");
        }
    }
}