package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtCronjob;
import java.util.List;

/**
 * (NxtCronjob)表服务接口
 *
 * @author makejava
 * @since 2020-10-18 16:11:36
 */
public interface NxtCronjobService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtCronjob queryById(Long id);

    /**
     * 通过Key查询单条数据
     *
     * @param key 唯一标识符
     * @return 实例对象
     */
    NxtCronjob queryByKey(String key);

    /**
     * 通过Key查询单条数据(带悲观锁)
     *
     * @param key 唯一标识符
     * @return 实例对象
     */
    NxtCronjob queryByKeyForUpdate(String key);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtCronjob> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nxtCronjob 实例对象
     * @return 实例对象
     */
    NxtCronjob insert(NxtCronjob nxtCronjob);

    /**
     * 修改数据
     *
     * @param nxtCronjob 实例对象
     * @return 实例对象
     */
    NxtCronjob update(NxtCronjob nxtCronjob);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}