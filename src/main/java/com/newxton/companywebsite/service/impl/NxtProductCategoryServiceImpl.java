package com.newxton.companywebsite.service.impl;

import com.newxton.companywebsite.entity.NxtProductCategory;
import com.newxton.companywebsite.dao.NxtProductCategoryDao;
import com.newxton.companywebsite.service.NxtProductCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtProductCategory)表服务实现类
 *
 * @author makejava
 * @since 2020-08-03 10:22:22
 */
@Service("nxtProductCategoryService")
public class NxtProductCategoryServiceImpl implements NxtProductCategoryService {
    @Resource
    private NxtProductCategoryDao nxtProductCategoryDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtProductCategory queryById(Long id) {
        return this.nxtProductCategoryDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtProductCategory> queryAllByLimit(int offset, int limit) {
        return this.nxtProductCategoryDao.queryAllByLimit(offset, limit);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtProductCategory 实例对象
     * @return 对象列表
     */
    public List<NxtProductCategory> queryAll(NxtProductCategory nxtProductCategory){
        return this.nxtProductCategoryDao.queryAll(nxtProductCategory);
    }

    /**
     * 新增数据
     *
     * @param nxtProductCategory 实例对象
     * @return 实例对象
     */
    @Override
    public NxtProductCategory insert(NxtProductCategory nxtProductCategory) {
        this.nxtProductCategoryDao.insert(nxtProductCategory);
        return nxtProductCategory;
    }

    /**
     * 修改数据
     *
     * @param nxtProductCategory 实例对象
     * @return 实例对象
     */
    @Override
    public NxtProductCategory update(NxtProductCategory nxtProductCategory) {
        this.nxtProductCategoryDao.update(nxtProductCategory);
        return this.queryById(nxtProductCategory.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtProductCategoryDao.deleteById(id) > 0;
    }
}