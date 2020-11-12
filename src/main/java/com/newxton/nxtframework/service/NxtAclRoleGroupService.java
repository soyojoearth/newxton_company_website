package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtAclGroupAction;
import com.newxton.nxtframework.entity.NxtAclRoleGroup;
import java.util.List;

/**
 * (NxtAclRoleGroup)表服务接口
 *
 * @author makejava
 * @since 2020-10-21 11:10:19
 */
public interface NxtAclRoleGroupService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtAclRoleGroup queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtAclRoleGroup> queryAllByLimit(int offset, int limit);

    List<NxtAclRoleGroup> queryAll(NxtAclRoleGroup nxtAclRoleGroup);

    /**
     * 新增数据
     *
     * @param nxtAclRoleGroup 实例对象
     * @return 实例对象
     */
    NxtAclRoleGroup insert(NxtAclRoleGroup nxtAclRoleGroup);

    /**
     * 修改数据
     *
     * @param nxtAclRoleGroup 实例对象
     * @return 实例对象
     */
    NxtAclRoleGroup update(NxtAclRoleGroup nxtAclRoleGroup);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}