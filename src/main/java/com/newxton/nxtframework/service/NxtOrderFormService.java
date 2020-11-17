package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtOrderForm;

import java.util.List;

/**
 * (NxtOrderForm)表服务接口
 *
 * @author makejava
 * @since 2020-11-14 21:41:50
 */
public interface NxtOrderFormService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtOrderForm queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtOrderForm> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nxtOrderForm 实例对象
     * @return 实例对象
     */
    NxtOrderForm insert(NxtOrderForm nxtOrderForm);

    /**
     * 修改数据
     *
     * @param nxtOrderForm 实例对象
     * @return 实例对象
     */
    NxtOrderForm update(NxtOrderForm nxtOrderForm);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}