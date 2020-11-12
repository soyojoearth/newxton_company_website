package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.entity.NxtAclGroupAction;
import com.newxton.nxtframework.entity.NxtAclRoleGroup;
import com.newxton.nxtframework.dao.NxtAclRoleGroupDao;
import com.newxton.nxtframework.service.NxtAclRoleGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtAclRoleGroup)表服务实现类
 *
 * @author makejava
 * @since 2020-10-21 11:10:19
 */
@Service("nxtAclRoleGroupService")
public class NxtAclRoleGroupServiceImpl implements NxtAclRoleGroupService {
    @Resource
    private NxtAclRoleGroupDao nxtAclRoleGroupDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtAclRoleGroup queryById(Long id) {
        return this.nxtAclRoleGroupDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtAclRoleGroup> queryAllByLimit(int offset, int limit) {
        return this.nxtAclRoleGroupDao.queryAllByLimit(offset, limit);
    }

    public List<NxtAclRoleGroup> queryAll(NxtAclRoleGroup nxtAclRoleGroup){
        return this.nxtAclRoleGroupDao.queryAll(nxtAclRoleGroup);
    }

    /**
     * 新增数据
     *
     * @param nxtAclRoleGroup 实例对象
     * @return 实例对象
     */
    @Override
    public NxtAclRoleGroup insert(NxtAclRoleGroup nxtAclRoleGroup) {
        this.nxtAclRoleGroupDao.insert(nxtAclRoleGroup);
        return nxtAclRoleGroup;
    }

    /**
     * 修改数据
     *
     * @param nxtAclRoleGroup 实例对象
     * @return 实例对象
     */
    @Override
    public NxtAclRoleGroup update(NxtAclRoleGroup nxtAclRoleGroup) {
        this.nxtAclRoleGroupDao.update(nxtAclRoleGroup);
        return this.queryById(nxtAclRoleGroup.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtAclRoleGroupDao.deleteById(id) > 0;
    }
}