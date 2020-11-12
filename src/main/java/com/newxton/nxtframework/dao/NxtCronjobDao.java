package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtCronjob;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (NxtCronjob)表数据库访问层
 *
 * @author makejava
 * @since 2020-10-18 16:11:34
 */
public interface NxtCronjobDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtCronjob queryById(Long id);

    /**
     * 通过Key查询单条数据
     *
     * @param key 唯一标识符
     * @return 实例对象
     */
    NxtCronjob queryByKey(String key);

    /**
     * 通过Key查询单条数据(带悲观锁)
     *
     * @param key 唯一标识符
     * @return 实例对象
     */
    NxtCronjob queryByKeyForUpdate(String key);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtCronjob> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtCronjob 实例对象
     * @return 对象列表
     */
    List<NxtCronjob> queryAll(NxtCronjob nxtCronjob);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtCronjob 实例对象
     * @return 对象列表
     */
    List<NxtCronjob> queryAllGreaterThanStatusDateline(NxtCronjob nxtCronjob);

    /**
     * 新增数据
     *
     * @param nxtCronjob 实例对象
     * @return 影响行数
     */
    int insert(NxtCronjob nxtCronjob);

    /**
     * 修改数据
     *
     * @param nxtCronjob 实例对象
     * @return 影响行数
     */
    int update(NxtCronjob nxtCronjob);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}