package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtAclAction;
import java.util.List;

/**
 * (NxtAclAction)表服务接口
 *
 * @author makejava
 * @since 2020-10-21 11:09:39
 */
public interface NxtAclActionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtAclAction queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtAclAction> queryAllByLimit(int offset, int limit);

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    List<NxtAclAction> queryAll(NxtAclAction nxtAclAction);

    /**
     * 新增数据
     *
     * @param nxtAclAction 实例对象
     * @return 实例对象
     */
    NxtAclAction insert(NxtAclAction nxtAclAction);

    /**
     * 修改数据
     *
     * @param nxtAclAction 实例对象
     * @return 实例对象
     */
    NxtAclAction update(NxtAclAction nxtAclAction);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}