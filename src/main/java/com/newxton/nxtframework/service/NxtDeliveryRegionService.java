package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtDeliveryRegion;
import java.util.List;

/**
 * (NxtDeliveryRegion)表服务接口
 *
 * @author makejava
 * @since 2020-11-02 19:03:31
 */
public interface NxtDeliveryRegionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtDeliveryRegion queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtDeliveryRegion> queryAllByLimit(int offset, int limit);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtDeliveryRegion 实例对象
     * @return 对象列表
     */
    List<NxtDeliveryRegion> queryAll(NxtDeliveryRegion nxtDeliveryRegion);

    /**
     * 新增数据
     *
     * @param nxtDeliveryRegion 实例对象
     * @return 实例对象
     */
    NxtDeliveryRegion insert(NxtDeliveryRegion nxtDeliveryRegion);

    /**
     * 修改数据
     *
     * @param nxtDeliveryRegion 实例对象
     * @return 实例对象
     */
    NxtDeliveryRegion update(NxtDeliveryRegion nxtDeliveryRegion);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}