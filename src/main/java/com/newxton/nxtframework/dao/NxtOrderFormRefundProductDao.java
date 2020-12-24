package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtOrderFormRefundProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtOrderFormRefundProduct)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-14 21:41:57
 */
public interface NxtOrderFormRefundProductDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtOrderFormRefundProduct queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtOrderFormRefundProduct> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtOrderFormRefundProduct 实例对象
     * @return 对象列表
     */
    List<NxtOrderFormRefundProduct> queryAll(NxtOrderFormRefundProduct nxtOrderFormRefundProduct);

    /**
     * 新增数据
     *
     * @param nxtOrderFormRefundProduct 实例对象
     * @return 影响行数
     */
    int insert(NxtOrderFormRefundProduct nxtOrderFormRefundProduct);


    /**
     * 修改数据
     *
     * @param nxtOrderFormRefundProduct 实例对象
     * @return 影响行数
     */
    int update(NxtOrderFormRefundProduct nxtOrderFormRefundProduct);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据退款服务单id列表查询指定行数据
     * @param idList
     * @return
     */
    List<NxtOrderFormRefundProduct> selectAllByOrderFormRefundIdSet(@Param("idList") List<Long> idList);

}