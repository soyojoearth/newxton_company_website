package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.entity.NxtTenant;
import com.newxton.nxtframework.dao.NxtTenantDao;
import com.newxton.nxtframework.service.NxtTenantService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtTenant)表服务实现类
 *
 * @author makejava
 * @since 2020-12-24 13:13:19
 */
@Service("nxtTenantService")
public class NxtTenantServiceImpl implements NxtTenantService {
    @Resource
    private NxtTenantDao nxtTenantDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtTenant queryById(Long id) {
        return this.nxtTenantDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtTenant> queryAllByLimit(int offset, int limit) {
        return this.nxtTenantDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtTenant 实例对象
     * @return 实例对象
     */
    @Override
    public NxtTenant insert(NxtTenant nxtTenant) {
        this.nxtTenantDao.insert(nxtTenant);
        return nxtTenant;
    }

    /**
     * 修改数据
     *
     * @param nxtTenant 实例对象
     * @return 实例对象
     */
    @Override
    public NxtTenant update(NxtTenant nxtTenant) {
        this.nxtTenantDao.update(nxtTenant);
        return this.queryById(nxtTenant.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtTenantDao.deleteById(id) > 0;
    }
}