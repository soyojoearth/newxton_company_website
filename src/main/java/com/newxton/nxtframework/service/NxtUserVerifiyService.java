package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtUserVerifiy;
import java.util.List;

/**
 * (NxtUserVerifiy)表服务接口
 *
 * @author makejava
 * @since 2020-11-28 11:29:49
 */
public interface NxtUserVerifiyService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtUserVerifiy queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtUserVerifiy> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nxtUserVerifiy 实例对象
     * @return 实例对象
     */
    NxtUserVerifiy insert(NxtUserVerifiy nxtUserVerifiy);

    /**
     * 修改数据
     *
     * @param nxtUserVerifiy 实例对象
     * @return 实例对象
     */
    NxtUserVerifiy update(NxtUserVerifiy nxtUserVerifiy);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}