package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtAclGroupAction;
import java.util.List;

/**
 * (NxtAclGroupAction)表服务接口
 *
 * @author makejava
 * @since 2020-10-21 11:10:19
 */
public interface NxtAclGroupActionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtAclGroupAction queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtAclGroupAction> queryAllByLimit(int offset, int limit);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtAclGroupAction 实例对象
     * @return 对象列表
     */
    List<NxtAclGroupAction> queryAll(NxtAclGroupAction nxtAclGroupAction);

    /**
     * 新增数据
     *
     * @param nxtAclGroupAction 实例对象
     * @return 实例对象
     */
    NxtAclGroupAction insert(NxtAclGroupAction nxtAclGroupAction);

    /**
     * 修改数据
     *
     * @param nxtAclGroupAction 实例对象
     * @return 实例对象
     */
    NxtAclGroupAction update(NxtAclGroupAction nxtAclGroupAction);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}