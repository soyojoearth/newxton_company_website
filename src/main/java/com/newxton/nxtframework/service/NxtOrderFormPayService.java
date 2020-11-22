package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtOrderFormPay;

import java.util.List;

/**
 * (NxtOrderFormPay)表服务接口
 *
 * @author makejava
 * @since 2020-11-14 21:41:53
 */
public interface NxtOrderFormPayService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtOrderFormPay queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtOrderFormPay> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nxtOrderFormPay 实例对象
     * @return 实例对象
     */
    NxtOrderFormPay insert(NxtOrderFormPay nxtOrderFormPay);

    /**
     * 修改数据
     *
     * @param nxtOrderFormPay 实例对象
     * @return 实例对象
     */
    NxtOrderFormPay update(NxtOrderFormPay nxtOrderFormPay);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}