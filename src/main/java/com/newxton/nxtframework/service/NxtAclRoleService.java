package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtAclGroupAction;
import com.newxton.nxtframework.entity.NxtAclRole;
import java.util.List;

/**
 * (NxtAclRole)表服务接口
 *
 * @author makejava
 * @since 2020-10-21 11:10:19
 */
public interface NxtAclRoleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtAclRole queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtAclRole> queryAllByLimit(int offset, int limit);

    List<NxtAclRole> queryAll(NxtAclRole nxtAclRole);

    /**
     * 新增数据
     *
     * @param nxtAclRole 实例对象
     * @return 实例对象
     */
    NxtAclRole insert(NxtAclRole nxtAclRole);

    /**
     * 修改数据
     *
     * @param nxtAclRole 实例对象
     * @return 实例对象
     */
    NxtAclRole update(NxtAclRole nxtAclRole);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}