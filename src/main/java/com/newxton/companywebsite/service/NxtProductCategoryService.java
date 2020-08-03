package com.newxton.companywebsite.service;

import com.newxton.companywebsite.entity.NxtProductCategory;

import java.util.List;

/**
 * (NxtProductCategory)表服务接口
 *
 * @author makejava
 * @since 2020-08-03 10:22:22
 */
public interface NxtProductCategoryService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtProductCategory queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtProductCategory> queryAllByLimit(int offset, int limit);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtProductCategory 实例对象
     * @return 对象列表
     */
    List<NxtProductCategory> queryAll(NxtProductCategory nxtProductCategory);

    /**
     * 新增数据
     *
     * @param nxtProductCategory 实例对象
     * @return 实例对象
     */
    NxtProductCategory insert(NxtProductCategory nxtProductCategory);

    /**
     * 修改数据
     *
     * @param nxtProductCategory 实例对象
     * @return 实例对象
     */
    NxtProductCategory update(NxtProductCategory nxtProductCategory);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}