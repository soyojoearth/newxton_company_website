package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtOrderFormProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtOrderFormProduct)表服务接口
 *
 * @author makejava
 * @since 2020-11-19 13:57:32
 */
public interface NxtOrderFormProductService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtOrderFormProduct queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtOrderFormProduct> queryAllByLimit(int offset, int limit);

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
     * @return 实例对象
     */
    NxtOrderFormProduct insert(NxtOrderFormProduct nxtOrderFormProduct);

    /**
     * 修改数据
     *
     * @param nxtOrderFormProduct 实例对象
     * @return 实例对象
     */
    NxtOrderFormProduct update(NxtOrderFormProduct nxtOrderFormProduct);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 根据orderFormId列表查询所有
     * @param idList
     * @return
     */
    List<NxtOrderFormProduct> selectAllByOrderFormIdSet(@Param("idList") List<Long> idList);

    /**
     * 根据Id列表查询所有
     * @param idList
     * @return
     */
    List<NxtOrderFormProduct> selectAllByIdSet(@Param("idList") List<Long> idList);

    /**
     * 根据ProductId列表查询
     * @param offset
     * @param limit
     * @param productId
     * @return
     */
    List<NxtOrderFormProduct> queryAllByProductIdLimit(@Param("offset") Long offset, @Param("limit") Long limit, @Param("productId") Long productId);

}