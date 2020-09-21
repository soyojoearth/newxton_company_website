package com.newxton.supercompanyapi.dao;

import com.newxton.supercompanyapi.entity.NxtNewsCategory;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (NxtNewsCategory)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-23 09:25:13
 */
public interface NxtNewsCategoryDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtNewsCategory queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtNewsCategory> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtNewsCategory 实例对象
     * @return 对象列表
     */
    List<NxtNewsCategory> queryAll(NxtNewsCategory nxtNewsCategory);

    /**
     * 新增数据
     *
     * @param nxtNewsCategory 实例对象
     * @return 影响行数
     */
    int insert(NxtNewsCategory nxtNewsCategory);

    /**
     * 修改数据
     *
     * @param nxtNewsCategory 实例对象
     * @return 影响行数
     */
    int update(NxtNewsCategory nxtNewsCategory);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}