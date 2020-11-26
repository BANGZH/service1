package com.cloud.service01.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {
    /**
     * 保存并更新token，请在需要生成token的方法上设置save=true，例如：页面跳转的方法
     */
    boolean save() default false;

    /**
     * 校验token，请在提交的方法上加上本属性
     */
    boolean check() default false;
}