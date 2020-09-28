package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.dao.NxtWebPageDao;
import com.newxton.nxtframework.entity.NxtWebPage;
import com.newxton.nxtframework.service.NxtWebPageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtWebPage)表服务实现类
 *
 * @author makejava
 * @since 2020-08-04 11:21:23
 */
@Service("nxtWebPageService")
public class NxtWebPageServiceImpl implements NxtWebPageService {
    @Resource
    private NxtWebPageDao nxtWebPageDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtWebPage queryById(Long id) {
        return this.nxtWebPageDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtWebPage> queryAllByLimit(int offset, int limit) {
        return this.nxtWebPageDao.queryAllByLimit(offset, limit);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtWebPage 实例对象
     * @return 对象列表
     */
    public List<NxtWebPage> queryAll(NxtWebPage nxtWebPage){
        return this.nxtWebPageDao.queryAll(nxtWebPage);
    }

    /**
     * 新增数据
     *
     * @param nxtWebPage 实例对象
     * @return 实例对象
     */
    @Override
    public NxtWebPage insert(NxtWebPage nxtWebPage) {
        this.nxtWebPageDao.insert(nxtWebPage);
        return nxtWebPage;
    }

    /**
     * 修改数据
     *
     * @param nxtWebPage 实例对象
     * @return 实例对象
     */
    @Override
    public NxtWebPage update(NxtWebPage nxtWebPage) {
        this.nxtWebPageDao.update(nxtWebPage);
        return this.queryById(nxtWebPage.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtWebPageDao.deleteById(id) > 0;
    }
}