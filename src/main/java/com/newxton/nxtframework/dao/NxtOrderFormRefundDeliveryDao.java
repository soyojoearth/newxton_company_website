package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtOrderFormRefundDelivery;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (NxtOrderFormRefundDelivery)表数据库访问层
 *
 * @author makejava
 * @since 2020-12-08 15:39:40
 */
public interface NxtOrderFormRefundDeliveryDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtOrderFormRefundDelivery queryById(Long id);

    /**
     * 通过orderFormRefundId查询单条数据
     *
     * @param orderFormRefundId
     * @return 实例对象
     */
    NxtOrderFormRefundDelivery queryByOrderFormRefundId(Long orderFormRefundId);


    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtOrderFormRefundDelivery> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtOrderFormRefundDelivery 实例对象
     * @return 对象列表
     */
    List<NxtOrderFormRefundDelivery> queryAll(NxtOrderFormRefundDelivery nxtOrderFormRefundDelivery);

    /**
     * 新增数据
     *
     * @param nxtOrderFormRefundDelivery 实例对象
     * @return 影响行数
     */
    int insert(NxtOrderFormRefundDelivery nxtOrderFormRefundDelivery);

    /**
     * 修改数据
     *
     * @param nxtOrderFormRefundDelivery 实例对象
     * @return 影响行数
     */
    int update(NxtOrderFormRefundDelivery nxtOrderFormRefundDelivery);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}