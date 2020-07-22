package com.newxton.companywebsite.service.impl;

import com.newxton.companywebsite.entity.NxtContentCategory;
import com.newxton.companywebsite.dao.NxtContentCategoryDao;
import com.newxton.companywebsite.service.NxtContentCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtContentCategory)表服务实现类
 *
 * @author makejava
 * @since 2020-07-22 15:24:14
 */
@Service("nxtContentCategoryService")
public class NxtContentCategoryServiceImpl implements NxtContentCategoryService {
    @Resource
    private NxtContentCategoryDao nxtContentCategoryDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtContentCategory queryById(Long id) {
        return this.nxtContentCategoryDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtContentCategory> queryAllByLimit(int offset, int limit) {
        return this.nxtContentCategoryDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtContentCategory 实例对象
     * @return 实例对象
     */
    @Override
    public NxtContentCategory insert(NxtContentCategory nxtContentCategory) {
        this.nxtContentCategoryDao.insert(nxtContentCategory);
        return nxtContentCategory;
    }

    /**
     * 修改数据
     *
     * @param nxtContentCategory 实例对象
     * @return 实例对象
     */
    @Override
    public NxtContentCategory update(NxtContentCategory nxtContentCategory) {
        this.nxtContentCategoryDao.update(nxtContentCategory);
        return this.queryById(nxtContentCategory.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtContentCategoryDao.deleteById(id) > 0;
    }
}