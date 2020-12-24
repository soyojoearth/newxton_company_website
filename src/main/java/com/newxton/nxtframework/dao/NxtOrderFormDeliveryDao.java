package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtOrderFormDelivery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtOrderFormDelivery)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-14 21:41:51
 */
public interface NxtOrderFormDeliveryDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtOrderFormDelivery queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtOrderFormDelivery> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtOrderFormDelivery 实例对象
     * @return 对象列表
     */
    List<NxtOrderFormDelivery> queryAll(NxtOrderFormDelivery nxtOrderFormDelivery);

    /**
     * 新增数据
     *
     * @param nxtOrderFormDelivery 实例对象
     * @return 影响行数
     */
    int insert(NxtOrderFormDelivery nxtOrderFormDelivery);

    /**
     * 修改数据
     *
     * @param nxtOrderFormDelivery 实例对象
     * @return 影响行数
     */
    int update(NxtOrderFormDelivery nxtOrderFormDelivery);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 通过orderFormId查询单条
     * @param orderFormId
     * @return
     */
    NxtOrderFormDelivery queryShippingByOrderFormId(@Param("orderFormId") Long orderFormId);

}