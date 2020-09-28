package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.dao.NxtNewsCategoryDao;
import com.newxton.nxtframework.entity.NxtNewsCategory;
import com.newxton.nxtframework.service.NxtNewsCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtNewsCategory)表服务实现类
 *
 * @author makejava
 * @since 2020-07-23 09:25:13
 */
@Service("nxtNewsCategoryService")
public class NxtNewsCategoryServiceImpl implements NxtNewsCategoryService {
    @Resource
    private NxtNewsCategoryDao nxtNewsCategoryDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtNewsCategory queryById(Long id) {
        return this.nxtNewsCategoryDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtNewsCategory> queryAllByLimit(int offset, int limit) {
        return this.nxtNewsCategoryDao.queryAllByLimit(offset, limit);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtNewsCategory 实例对象
     * @return 对象列表
     */
    public List<NxtNewsCategory> queryAll(NxtNewsCategory nxtNewsCategory){
        return this.nxtNewsCategoryDao.queryAll(nxtNewsCategory);
    }

    /**
     * 新增数据
     *
     * @param nxtNewsCategory 实例对象
     * @return 实例对象
     */
    @Override
    public NxtNewsCategory insert(NxtNewsCategory nxtNewsCategory) {
        this.nxtNewsCategoryDao.insert(nxtNewsCategory);
        return nxtNewsCategory;
    }

    /**
     * 修改数据
     *
     * @param nxtNewsCategory 实例对象
     * @return 实例对象
     */
    @Override
    public NxtNewsCategory update(NxtNewsCategory nxtNewsCategory) {
        this.nxtNewsCategoryDao.update(nxtNewsCategory);
        return this.queryById(nxtNewsCategory.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtNewsCategoryDao.deleteById(id) > 0;
    }
}