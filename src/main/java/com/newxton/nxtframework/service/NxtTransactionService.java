package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtTransaction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtTransaction)表服务接口
 *
 * @author makejava
 * @since 2020-11-14 21:45:50
 */
public interface NxtTransactionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtTransaction queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtTransaction> queryAllByLimit(int offset, int limit);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtTransaction 实例对象
     * @return 对象列表
     */
    List<NxtTransaction> queryAll(NxtTransaction nxtTransaction);

    /**
     * 新增数据
     *
     * @param nxtTransaction 实例对象
     * @return 实例对象
     */
    NxtTransaction insert(NxtTransaction nxtTransaction);

    /**
     * 修改数据
     *
     * @param nxtTransaction 实例对象
     * @return 实例对象
     */
    NxtTransaction update(NxtTransaction nxtTransaction);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 查询单个用户的余额
     * @return Long
     */
    Long queryBalanceSumByUserId(@Param("userId") Long userId);

    /**
     * 查询用户余额变动明细
     * @param offset
     * @param limit
     * @param userId
     * @return
     */
    List<NxtTransaction> queryAllByUserIdLimit(@Param("offset") Long offset, @Param("limit") Long limit, @Param("userId") Long userId);

}