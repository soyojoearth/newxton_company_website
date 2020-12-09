package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtUserLevel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtUserLevel)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-14 21:45:48
 */
public interface NxtUserLevelDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtUserLevel queryById(Long id);

    /**
     * 通过num查询单条数据
     *
     * @param num
     * @return 实例对象
     */
    NxtUserLevel queryByNum(Integer num);

    /**
     * 查询levelNum最大的那个
     * @return
     */
    NxtUserLevel queryMaxOne();

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtUserLevel> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtUserLevel 实例对象
     * @return 对象列表
     */
    List<NxtUserLevel> queryAll(NxtUserLevel nxtUserLevel);

    /**
     * 新增数据
     *
     * @param nxtUserLevel 实例对象
     * @return 影响行数
     */
    int insert(NxtUserLevel nxtUserLevel);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<NxtUserLevel> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<NxtUserLevel> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<NxtUserLevel> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<NxtUserLevel> entities);

    /**
     * 修改数据
     *
     * @param nxtUserLevel 实例对象
     * @return 影响行数
     */
    int update(NxtUserLevel nxtUserLevel);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}