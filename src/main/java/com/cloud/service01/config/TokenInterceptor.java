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

    /**
     * 使用Token控制重复提交方法</a><p>
     * 请求预处理方法 <p>
     * 对于标注@Token(save=true)的方法自动生成或刷新token，按URL来生成 <p>
     * 对于标注@Token(check=true)的方法校验客户端的token是否在session中存在,如果不存在抛异常 <p>
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            //只有设有注解的方法才处理
            Token annotation = method.getAnnotation(Token.class);
            if (annotation != null) {
                //以请求url为维度
                String url = getWholeUrl(request);
                boolean needCheck = annotation.check();
                if (needCheck) {
                    // 校验令牌
                    checkToken(request);
                }
                boolean needSave = annotation.save();
                if (needSave) {
                    // 保存或刷新令牌
                    saveToken(request, url);
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    public String getWholeUrl(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer(request.getRequestURI());
        String queryString = request.getQueryString();
        if (queryString != null) {
            sb.append('?').append(queryString);
        }
        return sb.toString();
    }

    /**
     * <p>生成或刷新Token,Token使用uuid生成.</p>
     * @param tokenKey 令牌key,目前是url
     * @since  2019年4月6日
     */
    private void saveToken(HttpServletRequest request, String tokenKey) {
        HttpSession session = request.getSession();
        Object tokenObj = session.getAttribute("tokenMap");
        Map<String, String> tokenMap = null;
        if (tokenObj == null) {
            // 如果tokenMap为空
            tokenMap = new HashMap<>();
            tokenMap.put(tokenKey, UUID.randomUUID().toString());
            session.setAttribute("tokenMap", tokenMap);
        } else if (tokenObj instanceof Map) {
            // 如果tokenMap已经存在,就直接覆盖更新
            tokenMap = (Map<String, String>) tokenObj;
            tokenMap.put(tokenKey, UUID.randomUUID().toString());
        }
        if (tokenMap != null) {
            request.setAttribute("token", tokenMap.get(tokenKey));
        }
    }

    /**
     * <p>Token校验,比对客户端传过来的token是否在session中存在.</p>
     * @since  2019年4月6日
     */
    private void checkToken(HttpServletRequest request) {
        // 判断客户端传过来的token是否在session的tokenMap中存在,存在则移除,不存在就是重复提交
        String tokenKey = null;
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new BusinessRuntimeException("当前会话已结束，请重新登录！");
        }
        Map<String, String> tokenMap = (Map<String, String>) session.getAttribute("tokenMap");
        if (tokenMap != null && !tokenMap.isEmpty()) {
            String clientToken = request.getParameter("token");
            if (clientToken != null) {
                for (Map.Entry<String, String> entry : tokenMap.entrySet()) {
                    if (clientToken.equals(entry.getValue())) {
                        tokenKey = entry.getKey();
                        // 刷新
                        // 如果tokenMap为空
                        tokenMap.put(tokenKey, UUID.randomUUID().toString());
                        session.setAttribute("tokenMap", tokenMap);
                        request.setAttribute("token", tokenMap.get(tokenKey));
                        break;
                    }
                }
            }
        }
        if (tokenKey == null) {
            // 如果最终没有在Session中找到已存在的Key
            throw new BusinessRuntimeException("当前页面已过期，请刷新页面后再试！或者您也可以在最后打开的页面中操作！");
        }
    }
}