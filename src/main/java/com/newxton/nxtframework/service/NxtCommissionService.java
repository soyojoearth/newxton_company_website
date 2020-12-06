package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtCommission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtCommission)表服务接口
 *
 * @author makejava
 * @since 2020-11-19 20:57:08
 */
public interface NxtCommissionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtCommission queryById(Long id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtCommission 实例对象
     * @return 对象列表
     */
    List<NxtCommission> queryAll(NxtCommission nxtCommission);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtCommission> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nxtCommission 实例对象
     * @return 实例对象
     */
    NxtCommission insert(NxtCommission nxtCommission);

    /**
     * 修改数据
     *
     * @param nxtCommission 实例对象
     * @return 实例对象
     */
    NxtCommission update(NxtCommission nxtCommission);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 根据userId查询指定行数据
     * @param offset
     * @param limit
     * @param userId
     * @return
     */
    List<NxtCommission> queryAllByUserIdLimit(@Param("offset") Long offset, @Param("limit") Long limit, @Param("userId") Long userId);

    /**
     * 根据userId查询所有已完成交易、未结转数据
     * @param userId
     * @return
     */
    List<NxtCommission> queryAllAllowTransferByUserId(@Param("userId") Long userId);

    /**
     * 根据userId查询所有已付款、等待交易完成的佣金记录
     * @param userId
     * @return
     */
    List<NxtCommission> queryAllWaitDealCompleateByUserId(@Param("userId") Long userId);

    /**
     * 查询所有已经确认收货，但还没有确认佣金完成，并且时间上可以确认佣金完成的单子
     * @param datelineReceivedLimit
     * @return
     */
    List<NxtCommission> queryAllWaittingEndingTooLongTime(@Param("datelineReceivedLimit") Long datelineReceivedLimit);

}