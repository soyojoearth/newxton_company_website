package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtOrderFormRefund;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtOrderFormRefund)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-14 21:41:54
 */
public interface NxtOrderFormRefundDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtOrderFormRefund queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtOrderFormRefund> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtOrderFormRefund 实例对象
     * @return 对象列表
     */
    List<NxtOrderFormRefund> queryAll(NxtOrderFormRefund nxtOrderFormRefund);

    /**
     * 新增数据
     *
     * @param nxtOrderFormRefund 实例对象
     * @return 影响行数
     */
    int insert(NxtOrderFormRefund nxtOrderFormRefund);
    

    /**
     * 修改数据
     *
     * @param nxtOrderFormRefund 实例对象
     * @return 影响行数
     */
    int update(NxtOrderFormRefund nxtOrderFormRefund);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 查询用户的售后服务单
     * @return
     */
    List<NxtOrderFormRefund> queryAllByUserIdAndLimit(Long offset,Long limit,Long userId,Boolean isDone,Boolean isShippedOrWaitShipping,Boolean isApplied);

    /**
     * 后台查询售后订单列表
     * @param offset
     * @param limit
     * @param status
     * @param userId
     * @param orderFormId
     * @return
     */
    List<NxtOrderFormRefund> adminQueryList(Long offset, Long limit, Integer status, Long userId, Long orderFormId);

    /**
     * 后台查询售后订单数量
     * @param offset
     * @param limit
     * @param status
     * @param userId
     * @param orderFormId
     * @return
     */
    Long adminQueryCount(Long offset, Long limit, Integer status, Long userId, Long orderFormId);

}