package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.entity.NxtAclGroupAction;
import com.newxton.nxtframework.entity.NxtAclUserRole;
import com.newxton.nxtframework.dao.NxtAclUserRoleDao;
import com.newxton.nxtframework.service.NxtAclUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtAclUserRole)表服务实现类
 *
 * @author makejava
 * @since 2020-10-21 11:10:19
 */
@Service("nxtAclUserRoleService")
public class NxtAclUserRoleServiceImpl implements NxtAclUserRoleService {
    @Resource
    private NxtAclUserRoleDao nxtAclUserRoleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtAclUserRole queryById(Long id) {
        return this.nxtAclUserRoleDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtAclUserRole> queryAllByLimit(int offset, int limit) {
        return this.nxtAclUserRoleDao.queryAllByLimit(offset, limit);
    }

    public List<NxtAclUserRole> queryAll(NxtAclUserRole nxtAclUserRole){
        return this.nxtAclUserRoleDao.queryAll(nxtAclUserRole);
    }

    /**
     * 新增数据
     *
     * @param nxtAclUserRole 实例对象
     * @return 实例对象
     */
    @Override
    public NxtAclUserRole insert(NxtAclUserRole nxtAclUserRole) {
        this.nxtAclUserRoleDao.insert(nxtAclUserRole);
        return nxtAclUserRole;
    }

    /**
     * 修改数据
     *
     * @param nxtAclUserRole 实例对象
     * @return 实例对象
     */
    @Override
    public NxtAclUserRole update(NxtAclUserRole nxtAclUserRole) {
        this.nxtAclUserRoleDao.update(nxtAclUserRole);
        return this.queryById(nxtAclUserRole.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtAclUserRoleDao.deleteById(id) > 0;
    }
}