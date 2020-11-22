package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtOrderFormRefundLog;

import java.util.List;

/**
 * (NxtOrderFormRefundLog)表服务接口
 *
 * @author makejava
 * @since 2020-11-14 21:41:55
 */
public interface NxtOrderFormRefundLogService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtOrderFormRefundLog queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtOrderFormRefundLog> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nxtOrderFormRefundLog 实例对象
     * @return 实例对象
     */
    NxtOrderFormRefundLog insert(NxtOrderFormRefundLog nxtOrderFormRefundLog);

    /**
     * 修改数据
     *
     * @param nxtOrderFormRefundLog 实例对象
     * @return 实例对象
     */
    NxtOrderFormRefundLog update(NxtOrderFormRefundLog nxtOrderFormRefundLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}