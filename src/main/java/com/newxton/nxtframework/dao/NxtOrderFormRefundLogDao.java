package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtOrderFormRefundLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtOrderFormRefundLog)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-14 21:41:55
 */
public interface NxtOrderFormRefundLogDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtOrderFormRefundLog queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtOrderFormRefundLog> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtOrderFormRefundLog 实例对象
     * @return 对象列表
     */
    List<NxtOrderFormRefundLog> queryAll(NxtOrderFormRefundLog nxtOrderFormRefundLog);

    /**
     * 新增数据
     *
     * @param nxtOrderFormRefundLog 实例对象
     * @return 影响行数
     */
    int insert(NxtOrderFormRefundLog nxtOrderFormRefundLog);

    /**
     * 修改数据
     *
     * @param nxtOrderFormRefundLog 实例对象
     * @return 影响行数
     */
    int update(NxtOrderFormRefundLog nxtOrderFormRefundLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}