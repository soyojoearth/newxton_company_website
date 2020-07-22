package com.newxton.companywebsite.dao;

import com.newxton.companywebsite.entity.NxtUploadfileCategory;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (NxtUploadfileCategory)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-22 15:23:31
 */
public interface NxtUploadfileCategoryDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtUploadfileCategory queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtUploadfileCategory> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


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
     * @return 影响行数
     */
    int insert(NxtUploadfileCategory nxtUploadfileCategory);

    /**
     * 修改数据
     *
     * @param nxtUploadfileCategory 实例对象
     * @return 影响行数
     */
    int update(NxtUploadfileCategory nxtUploadfileCategory);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}