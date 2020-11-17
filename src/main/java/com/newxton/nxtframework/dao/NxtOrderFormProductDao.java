package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtOrderFormProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtOrderFormProduct)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-14 21:41:53
 */
public interface NxtOrderFormProductDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtOrderFormProduct queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtOrderFormProduct> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtOrderFormProduct 实例对象
     * @return 对象列表
     */
    List<NxtOrderFormProduct> queryAll(NxtOrderFormProduct nxtOrderFormProduct);

    /**
     * 新增数据
     *
     * @param nxtOrderFormProduct 实例对象
     * @return 影响行数
     */
    int insert(NxtOrderFormProduct nxtOrderFormProduct);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<NxtOrderFormProduct> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<NxtOrderFormProduct> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<NxtOrderFormProduct> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<NxtOrderFormProduct> entities);

    /**
     * 修改数据
     *
     * @param nxtOrderFormProduct 实例对象
     * @return 影响行数
     */
    int update(NxtOrderFormProduct nxtOrderFormProduct);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}