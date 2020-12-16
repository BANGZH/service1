package com.cloud.service01.excel.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.service01.excel.domain.entity.DataSource;
import com.cloud.service01.excel.repository.DataSourceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * @ClassName DataSourceService
 * @Description: DataSourceService 类（或接口）是
 * @Author: zhonghanbang
 * @Date: 2020/11/2710:20
 */
@Slf4j
@Service
public class DataSourceService extends ServiceImpl<DataSourceMapper, DataSource> {


    final DataSourceMapper dataSourceMapper;

    final DataSourceDataService dataSourceDataService;


    public DataSourceService( DataSourceMapper dataSourceMapper, DataSourceDataService dataSourceDataService) {
        this.dataSourceMapper = dataSourceMapper;
        this.dataSourceDataService = dataSourceDataService;
    }


    /**
     * 获取当前用户可选数据源
     * @return
     */
    // public Map<String, Object> selectDataSourceOptions(String currentUser) {
    //     // 获取当前用户的数据源的角色组信息
    //     List<Integer> userDataSourceRoles = posterRoleService.selectUserDataSourceRoles(currentUser)
    //             .stream()
    //             .map(RoleVO::getRoleId)
    //             .collect(Collectors.toList());
    //     List<SourceOptionVO> sourceOptions = dataSourceMapper.selectUserAbleSourceName(userDataSourceRoles);
    //     HashMap<String,Object> result = new HashMap<>(1);
    //     result.put("sourceOptions",sourceOptions);
    //     return result;
    // }

    // /**
    //  * 获取数据源中的数据列表
    //  * @return
    //  */
    // public PageInfo<Object> selectSourceData(SourceDataDTO sourceDataDTO, String currentUser) {
    //     // 校验是否有权限查看
    //     DataSource dataSource = dataSourceMapper.selectById(sourceDataDTO.getDataSourceId());
    //     if (StringUtils.isEmpty(dataSource)) {
    //         throw new DataSourceException("找不到所选数据源，请刷新后重试.");
    //     }
    //     // 校验查看权限(是超管和数据源当前角色可以查看)
    //     List<RoleVO> userRoles = posterRoleService.selectUserDataSourceRoles(currentUser);
    //     Map<String, Integer> userRolesMap = userRoles.stream().collect(Collectors.toMap(RoleVO::getRoleName,RoleVO::getRoleId));
    //     if (!userRolesMap.containsKey(SUPER_ADMIN)) {
    //         boolean isLegalUser = userRoles.stream().map(RoleVO::getRoleId).collect(Collectors.toList()).contains(dataSource.getRoleId());
    //         if (!isLegalUser) {
    //             throw new DataSourceException("您无权限查看该数据源的数据，请联系管理员或重试.");
    //         }
    //     }
    //     List<String> title = JSON.parseArray(dataSource.getDataSourceTitle(),String.class);
    //     return dataSourceDataService.selectSourceDataByCondition(sourceDataDTO,title);
    // }

    // /**
    //  * 删除数据源中的数据
    //  * @return
    //  */
    // @Transactional(rollbackFor = Exception.class)
    // public void deleteSourceData(Integer sourceDataId,Integer dataId,String operator) {
    //     // 校验是否有权限查看
    //     DataSource dataSource = dataSourceMapper.selectById(sourceDataId);
    //     if (StringUtils.isEmpty(dataSource)) {
    //         throw new DataSourceException("找不到所选数据源，请刷新后重试.");
    //     }
    //     // 校验用户是否是创建人
    //     if (!dataSource.getCreator().equals(operator)) {
    //         throw new DataSourceException("您无权限操作,只能由数据源创建人操作.");
    //     }
    //     // 进行删除
    //     dataSourceDataService.deleteSourceData(dataId,sourceDataId);
    //     // 更新记录数
    //     dataSource.setRecordCount(dataSourceDataService.countSourceDataRecord(sourceDataId));
    //     dataSourceMapper.updateById(dataSource);
    // }

    /**
     * 获取数据源的标题列表
     * @return
     */
    // public Map<String, Object> selectDataSourceTitle(Integer sourceId, String operator) {
    //     posterRoleService.checkUserPermission(operator,null,false);
    //     DataSource dataSource = dataSourceMapper.selectById(sourceId);
    //     if (StringUtils.isEmpty(dataSource)) {
    //         throw new DataSourceException("找不到该数据源,请刷新后重试.");
    //     }
    //     HashMap<String,Object> result = new HashMap<>(1);
    //     result.put(TITLE, JSON.parseArray(dataSource.getDataSourceTitle(),String.class));
    //     return result;
    // }

    /**
     * 删除数据源 (逻辑删除)
     * 只能删除本人创建的数据源
     */
    // public void deleteSource(Integer dataSourceId,String operator) {
    //     DataSource dataSource = dataSourceMapper.selectById(dataSourceId);
    //     if (StringUtils.isEmpty(dataSource)) {
    //         throw new DataSourceException("找不到该数据源,请刷新后重试.");
    //     }
    //     if (!operator.equals(dataSource.getCreator())) {
    //         throw new DataSourceException("删除失败,只能删除本人创建的数据源.");
    //     }
    //     dataSource.setDataSourceStatus(0);
    //     dataSource.setUpdateTime(LocalDateTime.now());
    //     dataSourceMapper.updateById(dataSource);
    // }

    /**
     * 新增数据源基本信息
     */
    public DataSource insertDataSource(DataSource dataSource) {
        dataSourceMapper.insert(dataSource);
        return dataSource;
    }

    /**
     * 更新基本信息
     * @param dataSource
     * @return
     */
    public int updateDataSource(DataSource dataSource) {

        return dataSourceMapper.updateById(dataSource);
    }

    public DataSource selectDataSourceById(Integer dataSourceId) {
        return dataSourceMapper.selectById(dataSourceId);
    }

}
