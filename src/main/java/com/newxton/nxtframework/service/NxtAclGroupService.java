package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtAclGroup;
import com.newxton.nxtframework.entity.NxtAclGroupAction;

import java.util.List;

/**
 * (NxtAclGroup)表服务接口
 *
 * @author makejava
 * @since 2020-10-21 11:10:19
 */
public interface NxtAclGroupService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtAclGroup queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtAclGroup> queryAllByLimit(int offset, int limit);

    List<NxtAclGroup> queryAll(NxtAclGroup nxtAclGroup);

    /**
     * 新增数据
     *
     * @param nxtAclGroup 实例对象
     * @return 实例对象
     */
    NxtAclGroup insert(NxtAclGroup nxtAclGroup);

    /**
     * 修改数据
     *
     * @param nxtAclGroup 实例对象
     * @return 实例对象
     */
    NxtAclGroup update(NxtAclGroup nxtAclGroup);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}