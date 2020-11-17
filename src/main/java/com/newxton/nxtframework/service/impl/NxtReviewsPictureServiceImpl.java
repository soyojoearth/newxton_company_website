package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.dao.NxtReviewsPictureDao;
import com.newxton.nxtframework.entity.NxtReviewsPicture;
import com.newxton.nxtframework.service.NxtReviewsPictureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtReviewsPicture)表服务实现类
 *
 * @author makejava
 * @since 2020-11-14 21:45:46
 */
@Service("nxtReviewsPictureService")
public class NxtReviewsPictureServiceImpl implements NxtReviewsPictureService {
    @Resource
    private NxtReviewsPictureDao nxtReviewsPictureDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtReviewsPicture queryById(Long id) {
        return this.nxtReviewsPictureDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtReviewsPicture> queryAllByLimit(int offset, int limit) {
        return this.nxtReviewsPictureDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtReviewsPicture 实例对象
     * @return 实例对象
     */
    @Override
    public NxtReviewsPicture insert(NxtReviewsPicture nxtReviewsPicture) {
        this.nxtReviewsPictureDao.insert(nxtReviewsPicture);
        return nxtReviewsPicture;
    }

    /**
     * 修改数据
     *
     * @param nxtReviewsPicture 实例对象
     * @return 实例对象
     */
    @Override
    public NxtReviewsPicture update(NxtReviewsPicture nxtReviewsPicture) {
        this.nxtReviewsPictureDao.update(nxtReviewsPicture);
        return this.queryById(nxtReviewsPicture.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtReviewsPictureDao.deleteById(id) > 0;
    }
}