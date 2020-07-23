package com.newxton.companywebsite.service.impl;

import com.newxton.companywebsite.entity.NxtContent;
import com.newxton.companywebsite.dao.NxtContentDao;
import com.newxton.companywebsite.service.NxtContentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtContent)表服务实现类
 *
 * @author makejava
 * @since 2020-07-23 09:24:16
 */
@Service("nxtContentService")
public class NxtContentServiceImpl implements NxtContentService {
    @Resource
    private NxtContentDao nxtContentDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtContent queryById(Long id) {
        return this.nxtContentDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtContent> queryAllByLimit(int offset, int limit) {
        return this.nxtContentDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtContent 实例对象
     * @return 实例对象
     */
    @Override
    public NxtContent insert(NxtContent nxtContent) {
        this.nxtContentDao.insert(nxtContent);
        return nxtContent;
    }

    /**
     * 修改数据
     *
     * @param nxtContent 实例对象
     * @return 实例对象
     */
    @Override
    public NxtContent update(NxtContent nxtContent) {
        this.nxtContentDao.update(nxtContent);
        return this.queryById(nxtContent.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtContentDao.deleteById(id) > 0;
    }
}