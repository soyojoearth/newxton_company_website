package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtOrderFormRefundProduct;

import java.util.List;

/**
 * (NxtOrderFormRefundProduct)表服务接口
 *
 * @author makejava
 * @since 2020-11-14 21:41:57
 */
public interface NxtOrderFormRefundProductService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtOrderFormRefundProduct queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtOrderFormRefundProduct> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nxtOrderFormRefundProduct 实例对象
     * @return 实例对象
     */
    NxtOrderFormRefundProduct insert(NxtOrderFormRefundProduct nxtOrderFormRefundProduct);

    /**
     * 修改数据
     *
     * @param nxtOrderFormRefundProduct 实例对象
     * @return 实例对象
     */
    NxtOrderFormRefundProduct update(NxtOrderFormRefundProduct nxtOrderFormRefundProduct);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}