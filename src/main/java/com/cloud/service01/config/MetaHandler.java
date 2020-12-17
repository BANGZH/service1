package com.cloud.service01.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @ClassName MetaHandler
 * @Description: MetaHandler 类（或接口）是 配置自动填充创建时间,操作人信息
 * 处理新增和更新的基础数据填充，配合BaseEntity和MyBatisPlusConfig使用
 * @Author: bang
 * @Date: 2020/5/28 16:32
 */
@Component
public class MetaHandler implements MetaObjectHandler {

    /**
     * 新增数据执行
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String userName = "操作人";
        this.setInsertFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setInsertFieldValByName("creator", userName, metaObject);
        this.setInsertFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        this.setInsertFieldValByName("editor", userName, metaObject);
    }

    /**
     * 更新数据执行
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String userName = "操作人";
        this.setInsertFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        this.setInsertFieldValByName("editor",userName, metaObject);
    }
}
