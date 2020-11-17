package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtUserLevel;

import java.util.List;

/**
 * (NxtUserLevel)表服务接口
 *
 * @author makejava
 * @since 2020-11-14 21:45:48
 */
public interface NxtUserLevelService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtUserLevel queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtUserLevel> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nxtUserLevel 实例对象
     * @return 实例对象
     */
    NxtUserLevel insert(NxtUserLevel nxtUserLevel);

    /**
     * 修改数据
     *
     * @param nxtUserLevel 实例对象
     * @return 实例对象
     */
    NxtUserLevel update(NxtUserLevel nxtUserLevel);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}