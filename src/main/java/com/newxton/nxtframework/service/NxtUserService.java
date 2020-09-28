package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtUser;

import java.util.List;

/**
 * (NxtUser)表服务接口
 *
 * @author makejava
 * @since 2020-07-23 09:25:55
 */
public interface NxtUserService {

    /**
     * 通过username查询
     *
     * @param username 用户名
     * @return 实例对象
     */
    NxtUser queryByUsername(String username);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtUser queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtUser> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nxtUser 实例对象
     * @return 实例对象
     */
    NxtUser insert(NxtUser nxtUser);

    /**
     * 修改数据
     *
     * @param nxtUser 实例对象
     * @return 实例对象
     */
    NxtUser update(NxtUser nxtUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}