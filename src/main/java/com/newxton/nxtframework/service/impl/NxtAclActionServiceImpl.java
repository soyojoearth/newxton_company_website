package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.entity.NxtAclAction;
import com.newxton.nxtframework.dao.NxtAclActionDao;
import com.newxton.nxtframework.service.NxtAclActionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtAclAction)表服务实现类
 *
 * @author makejava
 * @since 2020-10-21 11:09:39
 */
@Service("nxtAclActionService")
public class NxtAclActionServiceImpl implements NxtAclActionService {
    @Resource
    private NxtAclActionDao nxtAclActionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtAclAction queryById(Long id) {
        return this.nxtAclActionDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtAclAction> queryAllByLimit(int offset, int limit) {
        return this.nxtAclActionDao.queryAllByLimit(offset, limit);
    }

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    public List<NxtAclAction> queryAll(NxtAclAction nxtAclAction) {
        return this.nxtAclActionDao.queryAll(nxtAclAction);
    }

    /**
     * 新增数据
     *
     * @param nxtAclAction 实例对象
     * @return 实例对象
     */
    @Override
    public NxtAclAction insert(NxtAclAction nxtAclAction) {
        this.nxtAclActionDao.insert(nxtAclAction);
        return nxtAclAction;
    }

    /**
     * 修改数据
     *
     * @param nxtAclAction 实例对象
     * @return 实例对象
     */
    @Override
    public NxtAclAction update(NxtAclAction nxtAclAction) {
        this.nxtAclActionDao.update(nxtAclAction);
        return this.queryById(nxtAclAction.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtAclActionDao.deleteById(id) > 0;
    }
}