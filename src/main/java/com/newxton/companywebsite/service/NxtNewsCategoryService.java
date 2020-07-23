package com.newxton.companywebsite.service;

import com.newxton.companywebsite.entity.NxtNewsCategory;
import java.util.List;

/**
 * (NxtNewsCategory)表服务接口
 *
 * @author makejava
 * @since 2020-07-23 09:25:13
 */
public interface NxtNewsCategoryService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtNewsCategory queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtNewsCategory> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nxtNewsCategory 实例对象
     * @return 实例对象
     */
    NxtNewsCategory insert(NxtNewsCategory nxtNewsCategory);

    /**
     * 修改数据
     *
     * @param nxtNewsCategory 实例对象
     * @return 实例对象
     */
    NxtNewsCategory update(NxtNewsCategory nxtNewsCategory);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}