package com.cloud.service01.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {
    /**
     * <p>保存并更新token，请在需要生成token的方法上设置save=true，例如：页面跳转的方法</p>
     * @since  2019年4月4日
     */
    boolean save() default false;
    /**
     * <p>校验token，请在提交的方法上加上本属性</p>
     * @since  2019年4月4日
     */
    boolean check() default false;
}