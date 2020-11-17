package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.dao.NxtReviewsDao;
import com.newxton.nxtframework.entity.NxtReviews;
import com.newxton.nxtframework.service.NxtReviewsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}