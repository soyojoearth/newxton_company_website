package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtWithdraw;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtWithdraw)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-14 21:45:50
 */
public interface NxtWithdrawDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtWithdraw queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtWithdraw> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtWithdraw 实例对象
     * @return 对象列表
     */
    List<NxtWithdraw> queryAll(NxtWithdraw nxtWithdraw);

    /**
     * 新增数据
     *
     * @param nxtWithdraw 实例对象
     * @return 影响行数
     */
    int insert(NxtWithdraw nxtWithdraw);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<NxtWithdraw> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<NxtWithdraw> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<NxtWithdraw> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<NxtWithdraw> entities);

    /**
     * 修改数据
     *
     * @param nxtWithdraw 实例对象
     * @return 影响行数
     */
    int update(NxtWithdraw nxtWithdraw);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

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