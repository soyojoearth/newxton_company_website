package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtUserLevel;
import org.springframework.data.relational.core.sql.In;

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
     * 通过num查询单条数据
     *
     * @param num
     * @return 实例对象
     */
    NxtUserLevel queryByNum(Integer num);

    /**
     * 查询levelNum最大的那个
     * @return
     */
    NxtUserLevel queryMaxOne();

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtUserLevel> queryAllByLimit(int offset, int limit);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtUserLevel 实例对象
     * @return 对象列表
     */
    List<NxtUserLevel> queryAll(NxtUserLevel nxtUserLevel);

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