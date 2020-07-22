package com.newxton.companywebsite.service;

import com.newxton.companywebsite.entity.NxtContentCategory;
import java.util.List;

/**
 * (NxtContentCategory)表服务接口
 *
 * @author makejava
 * @since 2020-07-22 15:24:14
 */
public interface NxtContentCategoryService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtContentCategory queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtContentCategory> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nxtContentCategory 实例对象
     * @return 实例对象
     */
    NxtContentCategory insert(NxtContentCategory nxtContentCategory);

    /**
     * 修改数据
     *
     * @param nxtContentCategory 实例对象
     * @return 实例对象
     */
    NxtContentCategory update(NxtContentCategory nxtContentCategory);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}