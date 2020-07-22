package com.newxton.companywebsite.dao;

import com.newxton.companywebsite.entity.NxtSetting;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (NxtSetting)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-22 15:23:53
 */
public interface NxtSettingDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtSetting queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtSetting> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtSetting 实例对象
     * @return 对象列表
     */
    List<NxtSetting> queryAll(NxtSetting nxtSetting);

    /**
     * 新增数据
     *
     * @param nxtSetting 实例对象
     * @return 影响行数
     */
    int insert(NxtSetting nxtSetting);

    /**
     * 修改数据
     *
     * @param nxtSetting 实例对象
     * @return 影响行数
     */
    int update(NxtSetting nxtSetting);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}