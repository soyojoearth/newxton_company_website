package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.entity.NxtAclGroupAction;
import com.newxton.nxtframework.dao.NxtAclGroupActionDao;
import com.newxton.nxtframework.service.NxtAclGroupActionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtAclGroupAction)表服务实现类
 *
 * @author makejava
 * @since 2020-10-21 11:10:19
 */
@Service("nxtAclGroupActionService")
public class NxtAclGroupActionServiceImpl implements NxtAclGroupActionService {
    @Resource
    private NxtAclGroupActionDao nxtAclGroupActionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtAclGroupAction queryById(Long id) {
        return this.nxtAclGroupActionDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtAclGroupAction> queryAllByLimit(int offset, int limit) {
        return this.nxtAclGroupActionDao.queryAllByLimit(offset, limit);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtAclGroupAction 实例对象
     * @return 对象列表
     */
    public List<NxtAclGroupAction> queryAll(NxtAclGroupAction nxtAclGroupAction){
        return this.nxtAclGroupActionDao.queryAll(nxtAclGroupAction);
    }

    /**
     * 新增数据
     *
     * @param nxtAclGroupAction 实例对象
     * @return 实例对象
     */
    @Override
    public NxtAclGroupAction insert(NxtAclGroupAction nxtAclGroupAction) {
        this.nxtAclGroupActionDao.insert(nxtAclGroupAction);
        return nxtAclGroupAction;
    }

    /**
     * 修改数据
     *
     * @param nxtAclGroupAction 实例对象
     * @return 实例对象
     */
    @Override
    public NxtAclGroupAction update(NxtAclGroupAction nxtAclGroupAction) {
        this.nxtAclGroupActionDao.update(nxtAclGroupAction);
        return this.queryById(nxtAclGroupAction.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtAclGroupActionDao.deleteById(id) > 0;
    }
}