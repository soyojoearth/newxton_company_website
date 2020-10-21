package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.entity.NxtAclGroup;
import com.newxton.nxtframework.dao.NxtAclGroupDao;
import com.newxton.nxtframework.entity.NxtAclGroupAction;
import com.newxton.nxtframework.service.NxtAclGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtAclGroup)表服务实现类
 *
 * @author makejava
 * @since 2020-10-21 11:10:19
 */
@Service("nxtAclGroupService")
public class NxtAclGroupServiceImpl implements NxtAclGroupService {
    @Resource
    private NxtAclGroupDao nxtAclGroupDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtAclGroup queryById(Long id) {
        return this.nxtAclGroupDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtAclGroup> queryAllByLimit(int offset, int limit) {
        return this.nxtAclGroupDao.queryAllByLimit(offset, limit);
    }

    public List<NxtAclGroup> queryAll(NxtAclGroup nxtAclGroup){
        return this.nxtAclGroupDao.queryAll(nxtAclGroup);
    }

    /**
     * 新增数据
     *
     * @param nxtAclGroup 实例对象
     * @return 实例对象
     */
    @Override
    public NxtAclGroup insert(NxtAclGroup nxtAclGroup) {
        this.nxtAclGroupDao.insert(nxtAclGroup);
        return nxtAclGroup;
    }

    /**
     * 修改数据
     *
     * @param nxtAclGroup 实例对象
     * @return 实例对象
     */
    @Override
    public NxtAclGroup update(NxtAclGroup nxtAclGroup) {
        this.nxtAclGroupDao.update(nxtAclGroup);
        return this.queryById(nxtAclGroup.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtAclGroupDao.deleteById(id) > 0;
    }
}