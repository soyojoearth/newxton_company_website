package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.entity.NxtAclGroupAction;
import com.newxton.nxtframework.entity.NxtAclRole;
import com.newxton.nxtframework.dao.NxtAclRoleDao;
import com.newxton.nxtframework.service.NxtAclRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtAclRole)表服务实现类
 *
 * @author makejava
 * @since 2020-10-21 11:10:19
 */
@Service("nxtAclRoleService")
public class NxtAclRoleServiceImpl implements NxtAclRoleService {
    @Resource
    private NxtAclRoleDao nxtAclRoleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtAclRole queryById(Long id) {
        return this.nxtAclRoleDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtAclRole> queryAllByLimit(int offset, int limit) {
        return this.nxtAclRoleDao.queryAllByLimit(offset, limit);
    }

    public List<NxtAclRole> queryAll(NxtAclRole nxtAclRole){
        return this.nxtAclRoleDao.queryAll(nxtAclRole);
    }

    /**
     * 新增数据
     *
     * @param nxtAclRole 实例对象
     * @return 实例对象
     */
    @Override
    public NxtAclRole insert(NxtAclRole nxtAclRole) {
        this.nxtAclRoleDao.insert(nxtAclRole);
        return nxtAclRole;
    }

    /**
     * 修改数据
     *
     * @param nxtAclRole 实例对象
     * @return 实例对象
     */
    @Override
    public NxtAclRole update(NxtAclRole nxtAclRole) {
        this.nxtAclRoleDao.update(nxtAclRole);
        return this.queryById(nxtAclRole.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtAclRoleDao.deleteById(id) > 0;
    }
}