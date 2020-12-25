package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtOrderFormLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtOrderFormLog)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-14 21:41:52
 */
public interface NxtOrderFormLogDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtOrderFormLog queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtOrderFormLog> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtOrderFormLog 实例对象
     * @return 对象列表
     */
    List<NxtOrderFormLog> queryAll(NxtOrderFormLog nxtOrderFormLog);

    /**
     * 新增数据
     *
     * @param nxtOrderFormLog 实例对象
     * @return 影响行数
     */
    int insert(NxtOrderFormLog nxtOrderFormLog);

    /**
     * 修改数据
     *
     * @param nxtOrderFormLog 实例对象
     * @return 影响行数
     */
    int update(NxtOrderFormLog nxtOrderFormLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}