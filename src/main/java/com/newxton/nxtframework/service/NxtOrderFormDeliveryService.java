package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtOrderFormDelivery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtOrderFormDelivery)表服务接口
 *
 * @author makejava
 * @since 2020-11-14 21:41:51
 */
public interface NxtOrderFormDeliveryService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtOrderFormDelivery queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtOrderFormDelivery> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nxtOrderFormDelivery 实例对象
     * @return 实例对象
     */
    NxtOrderFormDelivery insert(NxtOrderFormDelivery nxtOrderFormDelivery);

    /**
     * 修改数据
     *
     * @param nxtOrderFormDelivery 实例对象
     * @return 实例对象
     */
    NxtOrderFormDelivery update(NxtOrderFormDelivery nxtOrderFormDelivery);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 通过orderFormId查询单条
     * @param orderFormId
     * @return
     */
    NxtOrderFormDelivery queryShippingByOrderFormId(@Param("orderFormId") Long orderFormId);

}