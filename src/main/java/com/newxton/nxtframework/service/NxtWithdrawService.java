package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtWithdraw;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtWithdraw)表服务接口
 *
 * @author makejava
 * @since 2020-11-14 21:45:50
 */
public interface NxtWithdrawService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtWithdraw queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtWithdraw> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nxtWithdraw 实例对象
     * @return 实例对象
     */
    NxtWithdraw insert(NxtWithdraw nxtWithdraw);

    /**
     * 修改数据
     *
     * @param nxtWithdraw 实例对象
     * @return 实例对象
     */
    NxtWithdraw update(NxtWithdraw nxtWithdraw);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 查询某用户已经提现成功的总额
     * @param userId
     * @return
     */
    Long queryTotalWithdrawSuccessByUserId(@Param("userId") Long userId);

    /**
     * 查询某用户正在提现的总额
     * @param userId
     * @return
     */
    Long queryTotalWithdrawingByUserId(@Param("userId") Long userId);

    /**
     * 查询某用户被拒绝提现的总额
     * @param userId
     * @return
     */
    Long queryTotalWithdrawRejectedByUserId(@Param("userId") Long userId);

    /**
     * 后台查询列表
     * @param offset
     * @param limit
     * @param userId
     * @param status
     * @return
     */
    List<NxtWithdraw> adminQueryList(Long offset, Long limit, Long userId, Integer status);

    /**
     * 后台查询统计
     * @param userId
     * @param status
     * @return
     */
    Long adminQueryCount(Long userId, Integer status);

}