package com.newxton.companywebsite.dao;

import com.newxton.companywebsite.entity.NxtProduct;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (NxtProduct)表数据库访问层
 *
 * @author makejava
 * @since 2020-08-03 10:21:56
 */
public interface NxtProductDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtProduct queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtProduct> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 通过筛选条件查询指定行数据
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtProduct> selectAllByLimit(@Param("offset") int offset, @Param("limit") int limit,
                                      @Param("categoryId") Long categoryId);

    /**
     * 通过实体作为筛选条件查询Count
     *
     * @param nxtProduct 实例对象
     * @return 对象列表
     */
    Long queryCount(NxtProduct nxtProduct);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtProduct 实例对象
     * @return 对象列表
     */
    List<NxtProduct> queryAll(NxtProduct nxtProduct);

    /**
     * 新增数据
     *
     * @param nxtProduct 实例对象
     * @return 影响行数
     */
    int insert(NxtProduct nxtProduct);

    /**
     * 修改数据
     *
     * @param nxtProduct 实例对象
     * @return 影响行数
     */
    int update(NxtProduct nxtProduct);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}