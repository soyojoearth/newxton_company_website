package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtCommissionTransferIn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtCommissionTransferIn)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-14 21:44:38
 */
public interface NxtCommissionTransferInDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtCommissionTransferIn queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtCommissionTransferIn> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtCommissionTransferIn 实例对象
     * @return 对象列表
     */
    List<NxtCommissionTransferIn> queryAll(NxtCommissionTransferIn nxtCommissionTransferIn);

    /**
     * 新增数据
     *
     * @param nxtCommissionTransferIn 实例对象
     * @return 影响行数
     */
    int insert(NxtCommissionTransferIn nxtCommissionTransferIn);

    /**
     * 修改数据
     *
     * @param nxtCommissionTransferIn 实例对象
     * @return 影响行数
     */
    int update(NxtCommissionTransferIn nxtCommissionTransferIn);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据UserId查询结转中的收益统计（已提交结转申请，等待审核）
     * @param userId
     * @return
     */
    Long querySumIsTransferingByUserId(@Param("userId") Long userId);

    /**
     * 根据UserId查询结转被拒绝的的收益统计（已提交结转申请，审核被拒绝）
     * @param userId
     * @return
     */
    Long querySumIsTransferRejectedByUserId(@Param("userId") Long userId);

}