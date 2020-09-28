package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtUploadfileCategory;

import java.util.List;

/**
 * (NxtUploadfileCategory)表服务接口
 *
 * @author makejava
 * @since 2020-07-23 09:25:47
 */
public interface NxtUploadfileCategoryService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtUploadfileCategory queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtUploadfileCategory> queryAllByLimit(int offset, int limit);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtUploadfileCategory 实例对象
     * @return 对象列表
     */
    List<NxtUploadfileCategory> queryAll(NxtUploadfileCategory nxtUploadfileCategory);

    /**
     * 新增数据
     *
     * @param nxtUploadfileCategory 实例对象
     * @return 实例对象
     */
    NxtUploadfileCategory insert(NxtUploadfileCategory nxtUploadfileCategory);

    /**
     * 修改数据
     *
     * @param nxtUploadfileCategory 实例对象
     * @return 实例对象
     */
    NxtUploadfileCategory update(NxtUploadfileCategory nxtUploadfileCategory);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}