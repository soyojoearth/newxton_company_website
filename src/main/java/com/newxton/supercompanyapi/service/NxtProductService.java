package com.newxton.supercompanyapi.service;

import com.newxton.supercompanyapi.entity.NxtProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (NxtProduct)表服务接口
 *
 * @author makejava
 * @since 2020-08-03 10:21:56
 */
public interface NxtProductService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtProduct queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtProduct> queryAllByLimit(int offset, int limit);

    /**
     * 通过筛选条件查询指定行数据
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtProduct> selectAllByLimit(@Param("offset") int offset, @Param("limit") int limit,
                                      @Param("categoryId") Long categoryId);

    /**
     * 查询指定多个类型数据
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtProduct> selectByCategoryIdSet(@Param("offset") int offset, @Param("limit") int limit,
                                           @Param("categoryIdList") List<Long> categoryIdList);

    /**
     * 通过搜索关键字查询数据
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtProduct> searchAllByLimit(@Param("offset") int offset, @Param("limit") int limit,
                                      @Param("keyword") String keyword);

    /**
     * 通过分类id列表作为筛选条件查询Count
     * @return 对象列表
     */
    Long countByCategoryIdSet(@Param("categoryIdList") List<Long> categoryIdList);

    /**
     * 通过搜索关键字查询Count
     * @return 对象列表
     */
    Long searchAllCount(@Param("keyword") String keyword);

    /**
     * 通过实体作为筛选条件查询Count
     *
     * @param nxtProduct 实例对象
     * @return 对象列表
     */
    Long queryCount(NxtProduct nxtProduct);

    /**
     * 通过实体作为筛选条件查询，且关键词搜索，且分页
     *
     * @param map 万能map
     * @return 对象列表
     */
    List<NxtProduct> searchQueryAllByMap(Map<String,Object> map);

    /**
     * 通过实体作为筛选条件查询，且关键词搜索 （统计总数）
     *
     * @param map 万能map
     * @return Long
     */
    Long countSearchQueryAllByMap(Map<String,Object> map);

    /**
     * 新增数据
     *
     * @param nxtProduct 实例对象
     * @return 实例对象
     */
    NxtProduct insert(NxtProduct nxtProduct);

    /**
     * 修改数据
     *
     * @param nxtProduct 实例对象
     * @return 实例对象
     */
    NxtProduct update(NxtProduct nxtProduct);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}