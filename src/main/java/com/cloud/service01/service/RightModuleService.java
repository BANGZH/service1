package com.cloud.service01.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.service01.domain.entity.RightChildModule;
import com.cloud.service01.domain.entity.RightModule;
import com.cloud.service01.exception.RightModuleException;
import com.cloud.service01.repository.RightChildModuleMapper;
import com.cloud.service01.repository.RightModuleMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName RightModuleService
 * @Description: RightModuleService 类（或接口）是
 * @Author: zhonghanbang
 * @Date: 2020/11/2514:58
 */
@Service
public class RightModuleService {

    final RightModuleMapper rightModuleMapper;

    final RightChildModuleMapper rightChildModuleMapper;

    public RightModuleService(RightModuleMapper rightModuleMapper, RightChildModuleMapper rightChildModuleMapper) {
        this.rightModuleMapper = rightModuleMapper;
        this.rightChildModuleMapper = rightChildModuleMapper;
    }

    /**
     * 新增父模块
     * @return
     */
    public int addModule(RightModule rightModule,String operator) {
        // todo：校验权限
        checkPermission(operator);
        // 重名校验
        repeatModuleCheck(rightModule,false);
        return rightModuleMapper.insert(rightModule);

    }

    /**
     * 新增子模块
     * @return
     */
    public int addChildModule(RightChildModule childModule, String operator) {
        // todo：校验权限
        checkPermission(operator);
        // 重名校验
        repeatChildModuleCheck(childModule,false);
        return rightChildModuleMapper.insert(childModule);
    }

    /**
     * 更新父模块
     */
    public void updateModule(RightModule rightModule,String operator) {

    }

    /**
     * 更新子模块
     */
    public void updateChildModule(RightChildModule rightChildModule,String operator) {

    }

    /**
     * 删除的父模块
     */
    public void deleteModule(Integer moduleId,String operator) {
        checkPermission(operator);
        RightModule rightModule = new RightModule();
        rightModule.setId(moduleId);
        rightModule.setModuleStatus(0);
        rightModule.setEditor(operator);
        rightModule.setUpdateTime(LocalDateTime.now());
        rightModuleMapper.updateById(rightModule);
    }

    /**
     * 删除的子模块
     */
    public void deleteChildModule() {

    }

    /**
     * 校验父模块是否重名
     * @param rightModule
     * @param isUpdate
     */
    private void repeatModuleCheck(RightModule rightModule,boolean isUpdate) {
        List<RightModule> rightModules = rightModuleMapper.selectList(new QueryWrapper<RightModule>().lambda()
                .eq(RightModule::getModuleName,rightModule.getModuleName())
                .eq(RightModule::getModuleStatus,1)
        );
        if (isUpdate) {
            if (!rightModules.isEmpty() && !rightModules.get(0).getId().equals(rightModule.getId())) {
                throw new RightModuleException("已有该名称的模块,请更换模块名后重试.");
            }
            return;
        }
        if (!rightModules.isEmpty()) {
            throw new RightModuleException("已有该名称的模块,请更换模块名后重试.");
        }
    }

    /**
     * 校验父模块是否重名
     * @param childModule
     * @param isUpdate
     */
    private void repeatChildModuleCheck(RightChildModule childModule, boolean isUpdate) {
        List<RightChildModule> childModules = rightChildModuleMapper.selectList(new QueryWrapper<RightChildModule>().lambda()
                .eq(RightChildModule::getModuleName,childModule.getModuleName())
                .eq(RightChildModule::getFatherModuleId,childModule.getFatherModuleId())
                .eq(RightChildModule::getModuleStatus,1)
        );
        if (isUpdate) {
            if (!childModules.isEmpty() && !childModules.get(0).getId().equals(childModule.getId())) {
                throw new RightModuleException("已有该名称的模块,请更换模块名后重试.");
            }
            return;
        }
        if (!childModules.isEmpty()) {
            throw new RightModuleException("已有该名称的模块,请更换模块名后重试.");
        }
    }

    /**
     * 校验当前人的操作权限
     */
    private void checkPermission(String userCode) {

    }
}
