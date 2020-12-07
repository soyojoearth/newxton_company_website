package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtReviews;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtReviews)表服务接口
 *
 * @author makejava
 * @since 2020-11-14 21:45:45
 */
public interface NxtReviewsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtReviews queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtReviews> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nxtReviews 实例对象
     * @return 实例对象
     */
    NxtReviews insert(NxtReviews nxtReviews);

    /**
     * 修改数据
     *
     * @param nxtReviews 实例对象
     * @return 实例对象
     */
    NxtReviews update(NxtReviews nxtReviews);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 查询指定产品的评论列表-按时间倒序
     * @param offset
     * @param limit
     * @param productId
     * @return
     */
    List<NxtReviews> queryUserReviewsByProductId(@Param("offset") int offset, @Param("limit") int limit, @Param("productId") Long productId);

    /**
     * 查询指定产品的用户评论数量
     * @param productId
     * @return
     */
    Long queryUserReviewsCountByProductId(@Param("productId") Long productId);


    /**
     * 查询指定根评论的所有回复-按时间asc排序
     * @param idList
     * @return
     */
    List<NxtReviews> queryReviewsReplyByIdList(@Param("idList") List<Long> idList);

    /**
     * 查询指定订单的所有评论列表(不含回复)-按时间倒序
     * @param orderFormId
     * @return
     */
    List<NxtReviews> queryUserReviewsByOrderFormId(@Param("orderFormId") Long orderFormId);

    /**
     * 查询指定订单物品的根评论
     * @param orderFormProductId
     * @return
     */
    NxtReviews queryRootReviewsByOrderFormProductId(@Param("orderFormProductId") Long orderFormProductId);

    /**
     * 管理员后台，获取评论列表
     * @param offset
     * @param limit
     * @param productId
     * @return
     */
    List<NxtReviews> adminQueryReviewsList(Long offset, Long limit, Long productId);

    /**
     * 管理员后台，获取评论列表
     * @param offset
     * @param limit
     * @param productId
     * @return
     */
    Long adminQueryReviewsCount(Long offset, Long limit, Long productId);

}