package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtProduct;
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
     * 通过id类别批量查数据
     * @param offset
     * @param limit
     * @param idList
     * @return
     */
    List<NxtProduct> selectByIdSet(@Param("offset") int offset, @Param("limit") int limit, @Param("idList") List<Long> idList);

    /**
     * 查询指定多个类型数据
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtProduct> selectByCategoryIdSet(@Param("offset") int offset, @Param("limit") int limit,
                                           @Param("categoryIdList") List<Long> categoryIdList);

    /**
     * 通过搜索关键字、产品分类、排序方式查询数据
     * @param offset
     * @param limit
     * @param categoryIdList
     * @param keyword
     * @param orderType
     * @return
     */
    List<NxtProduct> searchAllByLimit(@Param("offset") int offset, @Param("limit") int limit,
                                      @Param("categoryIdList") List<Long> categoryIdList,
                                      @Param("keyword") String keyword, @Param("orderType") Integer orderType);


    /**
     * 通过搜索关键字、产品分类Count
     * @param categoryIdList
     * @param keyword
     * @return
     */
    Long searchAllCount(@Param("categoryIdList") List<Long> categoryIdList, @Param("keyword") String keyword);

    /**
     * 通过分类id列表作为筛选条件查询Count
     * @return 对象列表
     */
    Long countByCategoryIdSet(@Param("categoryIdList") List<Long> categoryIdList);


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
    List<NxtProduct> searchQueryAllByMap(Map<String, Object> map);

    /**
     * 通过实体作为筛选条件查询，且关键词搜索 （统计总数）
     *
     * @param map 万能map
     * @return Long
     */
    Long countSearchQueryAllByMap(Map<String, Object> map);

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

    /**
     * 前台获取热卖产品
     * @param limit
     * @return
     */
    List<NxtProduct> queryAllHot(int limit);

    /**
     * 前台获取新品
     * @param limit
     * @return
     */
    List<NxtProduct> queryAllNew(int limit);

    /**
     * 前台获取推荐产品
     * @param limit
     * @return
     */
    List<NxtProduct> queryAllRecommend(int limit);

    /**
     * 后台admin获取产品列表
     * @param offset
     * @param limit
     * @param categoryId
     * @param searchKeyword
     * @param isRecommend
     * @param isNew
     * @param isHot
     * @param isSelling
     * @return
     */
    List<NxtProduct> adminQueryAllByLimit(@Param("offset") Long offset, @Param("limit") Long limit, @Param("categoryId") Long categoryId, @Param("searchKeyword") String searchKeyword,@Param("isRecommend") Boolean isRecommend,@Param("isNew") Boolean isNew,@Param("isHot") Boolean isHot,@Param("isSelling") Boolean isSelling,@Param("isTrash") Boolean isTrash);

    /**
     * 后台admin统计产品总数
     * @param categoryId
     * @param searchKeyword
     * @param isRecommend
     * @param isNew
     * @param isHot
     * @param isSelling
     * @return
     */
    Long adminCountAll(@Param("categoryId") Long categoryId, @Param("searchKeyword") String searchKeyword,@Param("isRecommend") Boolean isRecommend,@Param("isNew") Boolean isNew,@Param("isHot") Boolean isHot,@Param("isSelling") Boolean isSelling,@Param("isTrash") Boolean isTrash);

    /**
     * 后台admin统计某个运费模版的产品数量
     * @param deliveryConfigId
     * @return
     */
    Long adminCountByDeliveryConfigId(Long deliveryConfigId);

}