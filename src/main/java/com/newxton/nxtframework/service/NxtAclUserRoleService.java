package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtAclGroupAction;
import com.newxton.nxtframework.entity.NxtAclUserRole;
import java.util.List;

/**
 * (NxtAclUserRole)表服务接口
 *
 * @author makejava
 * @since 2020-10-21 11:10:19
 */
public interface NxtAclUserRoleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtAclUserRole queryById(Long id);

    /**
     * 通过userId查询单条数据
     *
     * @param userId
     * @return 实例对象
     */
    NxtAclUserRole queryByUserId(Long userId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtAclUserRole> queryAllByLimit(int offset, int limit);

    List<NxtAclUserRole> queryAll(NxtAclUserRole nxtAclUserRole);

    /**
     * 新增数据
     *
     * @param nxtAclUserRole 实例对象
     * @return 实例对象
     */
    NxtAclUserRole insert(NxtAclUserRole nxtAclUserRole);

    /**
     * 修改数据
     *
     * @param nxtAclUserRole 实例对象
     * @return 实例对象
     */
    NxtAclUserRole update(NxtAclUserRole nxtAclUserRole);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}