package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.dao.NxtBannerDao;
import com.newxton.nxtframework.entity.NxtBanner;
import com.newxton.nxtframework.service.NxtBannerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtBanner)表服务实现类
 *
 * @author makejava
 * @since 2020-08-26 16:48:16
 */
@Service("nxtBannerService")
public class NxtBannerServiceImpl implements NxtBannerService {
    @Resource
    private NxtBannerDao nxtBannerDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtBanner queryById(Long id) {
        return this.nxtBannerDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtBanner> queryAllByLimit(int offset, int limit) {
        return this.nxtBannerDao.queryAllByLimit(offset, limit);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtBanner 实例对象
     * @return 对象列表
     */
    public List<NxtBanner> queryAll(NxtBanner nxtBanner){
        return this.nxtBannerDao.queryAll(nxtBanner);
    }

    /**
     * 新增数据
     *
     * @param nxtBanner 实例对象
     * @return 实例对象
     */
    @Override
    public NxtBanner insert(NxtBanner nxtBanner) {
        this.nxtBannerDao.insert(nxtBanner);
        return nxtBanner;
    }

    /**
     * 修改数据
     *
     * @param nxtBanner 实例对象
     * @return 实例对象
     */
    @Override
    public NxtBanner update(NxtBanner nxtBanner) {
        this.nxtBannerDao.update(nxtBanner);
        return this.queryById(nxtBanner.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtBannerDao.deleteById(id) > 0;
    }
}