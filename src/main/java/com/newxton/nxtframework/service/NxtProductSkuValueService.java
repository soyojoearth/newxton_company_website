package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtProductSkuValue;

import java.util.List;

/**
 * (NxtProductSkuValue)表服务接口
 *
 * @author makejava
 * @since 2020-08-26 09:16:41
 */
public interface NxtProductSkuValueService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtProductSkuValue queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtProductSkuValue> queryAllByLimit(int offset, int limit);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtProductSkuValue 实例对象
     * @return 对象列表
     */
    List<NxtProductSkuValue> queryAll(NxtProductSkuValue nxtProductSkuValue);

    /**
     * 新增数据
     *
     * @param nxtProductSkuValue 实例对象
     * @return 实例对象
     */
    NxtProductSkuValue insert(NxtProductSkuValue nxtProductSkuValue);

    /**
     * 修改数据
     *
     * @param nxtProductSkuValue 实例对象
     * @return 实例对象
     */
    NxtProductSkuValue update(NxtProductSkuValue nxtProductSkuValue);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}