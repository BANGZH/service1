package com.cloud.service01.excel.domain.constant;

import lombok.Getter;

/**
 * @ClassName DataSourceConstant
 * @Description: DataSourceConstant 类（或接口）是 Vip客户模块的常量类
 * @Author: zhonghanbang
 * @Date: 2020/11/30 12:02
 */
@Getter
public class DataSourceConstant {

    private DataSourceConstant(){}

    public static final String SUPER_ADMIN = "超级管理员";

    /**
     * 规定的导入数据源的员工工号标题
     */
    public static final String IMPORT_EMPLOYEE = "企业微信ID";

    public static final String TITLE = "title";

}
