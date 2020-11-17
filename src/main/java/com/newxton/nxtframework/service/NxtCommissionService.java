package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtCommission;

import java.util.List;

/**
 * (NxtCommission)表服务接口
 *
 * @author makejava
 * @since 2020-11-14 21:44:39
 */
public interface NxtCommissionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtCommission queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtCommission> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nxtCommission 实例对象
     * @return 实例对象
     */
    NxtCommission insert(NxtCommission nxtCommission);

    /**
     * 修改数据
     *
     * @param nxtCommission 实例对象
     * @return 实例对象
     */
    NxtCommission update(NxtCommission nxtCommission);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}