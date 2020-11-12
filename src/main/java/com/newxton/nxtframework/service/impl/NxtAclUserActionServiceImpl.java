package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.entity.NxtAclGroupAction;
import com.newxton.nxtframework.entity.NxtAclUserAction;
import com.newxton.nxtframework.dao.NxtAclUserActionDao;
import com.newxton.nxtframework.service.NxtAclUserActionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtAclUserAction)表服务实现类
 *
 * @author makejava
 * @since 2020-10-21 11:10:19
 */
@Service("nxtAclUserActionService")
public class NxtAclUserActionServiceImpl implements NxtAclUserActionService {
    @Resource
    private NxtAclUserActionDao nxtAclUserActionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtAclUserAction queryById(Long id) {
        return this.nxtAclUserActionDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtAclUserAction> queryAllByLimit(int offset, int limit) {
        return this.nxtAclUserActionDao.queryAllByLimit(offset, limit);
    }

    public List<NxtAclUserAction> queryAll(NxtAclUserAction nxtAclUserAction){
        return this.nxtAclUserActionDao.queryAll(nxtAclUserAction);
    }

    /**
     * 新增数据
     *
     * @param nxtAclUserAction 实例对象
     * @return 实例对象
     */
    @Override
    public NxtAclUserAction insert(NxtAclUserAction nxtAclUserAction) {
        this.nxtAclUserActionDao.insert(nxtAclUserAction);
        return nxtAclUserAction;
    }

    /**
     * 修改数据
     *
     * @param nxtAclUserAction 实例对象
     * @return 实例对象
     */
    @Override
    public NxtAclUserAction update(NxtAclUserAction nxtAclUserAction) {
        this.nxtAclUserActionDao.update(nxtAclUserAction);
        return this.queryById(nxtAclUserAction.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtAclUserActionDao.deleteById(id) > 0;
    }
}