package com.newxton.companywebsite.dao;

import com.newxton.companywebsite.entity.NxtContentCategory;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (NxtContentCategory)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-22 15:24:14
 */
public interface NxtContentCategoryDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtContentCategory queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtContentCategory> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtContentCategory 实例对象
     * @return 对象列表
     */
    List<NxtContentCategory> queryAll(NxtContentCategory nxtContentCategory);

    /**
     * 新增数据
     *
     * @param nxtContentCategory 实例对象
     * @return 影响行数
     */
    int insert(NxtContentCategory nxtContentCategory);

    /**
     * 修改数据
     *
     * @param nxtContentCategory 实例对象
     * @return 影响行数
     */
    int update(NxtContentCategory nxtContentCategory);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}