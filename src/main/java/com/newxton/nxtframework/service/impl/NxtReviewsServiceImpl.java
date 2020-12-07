package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.dao.NxtReviewsDao;
import com.newxton.nxtframework.entity.NxtReviews;
import com.newxton.nxtframework.service.NxtReviewsService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (NxtReviews)表服务实现类
 *
 * @author makejava
 * @since 2020-11-14 21:45:45
 */
@Service("nxtReviewsService")
public class NxtReviewsServiceImpl implements NxtReviewsService {
    @Resource
    private NxtReviewsDao nxtReviewsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtReviews queryById(Long id) {
        return this.nxtReviewsDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtReviews> queryAllByLimit(int offset, int limit) {
        return this.nxtReviewsDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtReviews 实例对象
     * @return 实例对象
     */
    @Override
    public NxtReviews insert(NxtReviews nxtReviews) {
        this.nxtReviewsDao.insert(nxtReviews);
        return nxtReviews;
    }

    /**
     * 修改数据
     *
     * @param nxtReviews 实例对象
     * @return 实例对象
     */
    @Override
    public NxtReviews update(NxtReviews nxtReviews) {
        this.nxtReviewsDao.update(nxtReviews);
        return this.queryById(nxtReviews.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtReviewsDao.deleteById(id) > 0;
    }

    /**
     * 查询指定产品的评论列表-按时间倒序
     * @param offset
     * @param limit
     * @param productId
     * @return
     */
    public List<NxtReviews> queryUserReviewsByProductId(@Param("offset") int offset, @Param("limit") int limit, @Param("productId") Long productId){
        return this.nxtReviewsDao.queryUserReviewsByProductId(offset,limit,productId);
    }

    /**
     * 查询指定产品的用户评论数量
     * @param productId
     * @return
     */
    public Long queryUserReviewsCountByProductId(@Param("productId") Long productId){
        return this.nxtReviewsDao.queryUserReviewsCountByProductId(productId);
    }


    /**
     * 查询指定根评论的所有回复-按时间asc排序
     * @param idList
     * @return
     */
    public List<NxtReviews> queryReviewsReplyByIdList(@Param("idList") List<Long> idList){
        if (idList.size()==0){
            return new ArrayList<>();
        }
        return this.nxtReviewsDao.queryReviewsReplyByIdList(idList);
    }

    /**
     * 查询指定订单的所有评论列表(不含回复)-按时间倒序
     * @param orderFormId
     * @return
     */
    public List<NxtReviews> queryUserReviewsByOrderFormId(@Param("orderFormId") Long orderFormId){
        return this.nxtReviewsDao.queryUserReviewsByOrderFormId(orderFormId);
    }

    /**
     * 查询指定订单物品的根评论
     * @param orderFormProductId
     * @return
     */
    public NxtReviews queryRootReviewsByOrderFormProductId(@Param("orderFormProductId") Long orderFormProductId){
        return this.nxtReviewsDao.queryRootReviewsByOrderFormProductId(orderFormProductId);
    }

    /**
     * 管理员后台，获取评论列表
     * @param offset
     * @param limit
     * @param productId
     * @return
     */
    public List<NxtReviews> adminQueryReviewsList(Long offset, Long limit, Long productId){
        return this.nxtReviewsDao.adminQueryReviewsList(offset, limit, productId);
    }

    /**
     * 管理员后台，获取评论列表
     * @param offset
     * @param limit
     * @param productId
     * @return
     */
    public Long adminQueryReviewsCount(Long offset, Long limit, Long productId){
        return this.nxtReviewsDao.adminQueryReviewsCount(offset, limit, productId);
    }

}