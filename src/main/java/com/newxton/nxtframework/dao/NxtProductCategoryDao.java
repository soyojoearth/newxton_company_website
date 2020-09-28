package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtProductCategory)表数据库访问层
 *
 * @author makejava
 * @since 2020-08-03 10:22:22
 */
public interface NxtProductCategoryDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtProductCategory queryById(Long id);

    /**
     * 通过Name查询单条数据
     *
     * @param categoryName 名称
     * @return 实例对象
     */
    NxtProductCategory queryByName(@Param("categoryName") String categoryName);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtProductCategory> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


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
     * @return 影响行数
     */
    int insert(NxtProductCategory nxtProductCategory);

    /**
     * 修改数据
     *
     * @param nxtProductCategory 实例对象
     * @return 影响行数
     */
    int update(NxtProductCategory nxtProductCategory);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}