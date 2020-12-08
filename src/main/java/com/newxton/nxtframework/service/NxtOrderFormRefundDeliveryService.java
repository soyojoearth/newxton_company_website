package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtOrderFormRefundDelivery;
import java.util.List;

/**
 * (NxtOrderFormRefundDelivery)表服务接口
 *
 * @author makejava
 * @since 2020-12-08 15:39:40
 */
public interface NxtOrderFormRefundDeliveryService {

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
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtOrderFormRefundDelivery> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nxtOrderFormRefundDelivery 实例对象
     * @return 实例对象
     */
    NxtOrderFormRefundDelivery insert(NxtOrderFormRefundDelivery nxtOrderFormRefundDelivery);

    /**
     * 修改数据
     *
     * @param nxtOrderFormRefundDelivery 实例对象
     * @return 实例对象
     */
    NxtOrderFormRefundDelivery update(NxtOrderFormRefundDelivery nxtOrderFormRefundDelivery);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}