package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtOrderFormRefund;

import java.util.List;

/**
 * (NxtOrderFormRefund)表服务接口
 *
 * @author makejava
 * @since 2020-11-14 21:41:55
 */
public interface NxtOrderFormRefundService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtOrderFormRefund queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtOrderFormRefund> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nxtOrderFormRefund 实例对象
     * @return 实例对象
     */
    NxtOrderFormRefund insert(NxtOrderFormRefund nxtOrderFormRefund);

    /**
     * 修改数据
     *
     * @param nxtOrderFormRefund 实例对象
     * @return 实例对象
     */
    NxtOrderFormRefund update(NxtOrderFormRefund nxtOrderFormRefund);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

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