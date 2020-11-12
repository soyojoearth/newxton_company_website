package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.dao.NxtUploadfileCategoryDao;
import com.newxton.nxtframework.entity.NxtUploadfileCategory;
import com.newxton.nxtframework.service.NxtUploadfileCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtUploadfileCategory)表服务实现类
 *
 * @author makejava
 * @since 2020-07-23 09:25:47
 */
@Service("nxtUploadfileCategoryService")
public class NxtUploadfileCategoryServiceImpl implements NxtUploadfileCategoryService {
    @Resource
    private NxtUploadfileCategoryDao nxtUploadfileCategoryDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtUploadfileCategory queryById(Long id) {
        return this.nxtUploadfileCategoryDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtUploadfileCategory> queryAllByLimit(int offset, int limit) {
        return this.nxtUploadfileCategoryDao.queryAllByLimit(offset, limit);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtUploadfileCategory 实例对象
     * @return 对象列表
     */
    public List<NxtUploadfileCategory> queryAll(NxtUploadfileCategory nxtUploadfileCategory){
        return this.nxtUploadfileCategoryDao.queryAll(nxtUploadfileCategory);
    }

    /**
     * 新增数据
     *
     * @param nxtUploadfileCategory 实例对象
     * @return 实例对象
     */
    @Override
    public NxtUploadfileCategory insert(NxtUploadfileCategory nxtUploadfileCategory) {
        this.nxtUploadfileCategoryDao.insert(nxtUploadfileCategory);
        return nxtUploadfileCategory;
    }

    /**
     * 修改数据
     *
     * @param nxtUploadfileCategory 实例对象
     * @return 实例对象
     */
    @Override
    public NxtUploadfileCategory update(NxtUploadfileCategory nxtUploadfileCategory) {
        this.nxtUploadfileCategoryDao.update(nxtUploadfileCategory);
        return this.queryById(nxtUploadfileCategory.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtUploadfileCategoryDao.deleteById(id) > 0;
    }
}