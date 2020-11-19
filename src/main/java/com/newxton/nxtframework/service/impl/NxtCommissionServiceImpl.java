package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.entity.NxtCommission;
import com.newxton.nxtframework.dao.NxtCommissionDao;
import com.newxton.nxtframework.service.NxtCommissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtCommission)表服务实现类
 *
 * @author makejava
 * @since 2020-11-19 20:57:08
 */
@Service("nxtCommissionService")
public class NxtCommissionServiceImpl implements NxtCommissionService {
    @Resource
    private NxtCommissionDao nxtCommissionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtCommission queryById(Long id) {
        return this.nxtCommissionDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtCommission> queryAllByLimit(int offset, int limit) {
        return this.nxtCommissionDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtCommission 实例对象
     * @return 实例对象
     */
    @Override
    public NxtCommission insert(NxtCommission nxtCommission) {
        this.nxtCommissionDao.insert(nxtCommission);
        return nxtCommission;
    }

    /**
     * 修改数据
     *
     * @param nxtCommission 实例对象
     * @return 实例对象
     */
    @Override
    public NxtCommission update(NxtCommission nxtCommission) {
        this.nxtCommissionDao.update(nxtCommission);
        return this.queryById(nxtCommission.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtCommissionDao.deleteById(id) > 0;
    }
}