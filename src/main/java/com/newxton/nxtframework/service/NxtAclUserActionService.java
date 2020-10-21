package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtAclGroupAction;
import com.newxton.nxtframework.entity.NxtAclUserAction;
import java.util.List;

/**
 * (NxtAclUserAction)表服务接口
 *
 * @author makejava
 * @since 2020-10-21 11:10:19
 */
public interface NxtAclUserActionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtAclUserAction queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtAclUserAction> queryAllByLimit(int offset, int limit);

    List<NxtAclUserAction> queryAll(NxtAclUserAction nxtAclUserAction);

    /**
     * 新增数据
     *
     * @param nxtAclUserAction 实例对象
     * @return 实例对象
     */
    NxtAclUserAction insert(NxtAclUserAction nxtAclUserAction);

    /**
     * 修改数据
     *
     * @param nxtAclUserAction 实例对象
     * @return 实例对象
     */
    NxtAclUserAction update(NxtAclUserAction nxtAclUserAction);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}