package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtTransaction;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

/**
 * (NxtTransaction)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-14 21:45:50
 */
public interface NxtTransactionDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtTransaction queryById(Long id);

    /**
     * 根据type、outId查询单个
     * @param type
     * @param outerId
     * @return
     */
    NxtTransaction queryByTypeAndOuterId(Integer type, Long outerId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtTransaction> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


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
     * @return 影响行数
     */
    int insert(NxtTransaction nxtTransaction);


    /**
     * 修改数据
     *
     * @param nxtTransaction 实例对象
     * @return 影响行数
     */
    int update(NxtTransaction nxtTransaction);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);


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

    /**
     * 后台查询资金log列表
     * @param offset
     * @param limit
     * @param userId
     * @param type
     * @return
     */
    List<NxtTransaction> adminQueryList(Long offset, Long limit, Long userId, Integer type);

    /**
     * 后台查询资金log列表
     * @param userId
     * @param type
     * @return
     */
    Long adminQueryCount(Long userId, Integer type);

}