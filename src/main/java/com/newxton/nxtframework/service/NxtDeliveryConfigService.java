package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtDeliveryConfig;
import java.util.List;

/**
 * (NxtDeliveryConfig)表服务接口
 *
 * @author makejava
 * @since 2020-11-02 19:03:31
 */
public interface NxtDeliveryConfigService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtDeliveryConfig queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtDeliveryConfig> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nxtDeliveryConfig 实例对象
     * @return 实例对象
     */
    NxtDeliveryConfig insert(NxtDeliveryConfig nxtDeliveryConfig);

    /**
     * 修改数据
     *
     * @param nxtDeliveryConfig 实例对象
     * @return 实例对象
     */
    NxtDeliveryConfig update(NxtDeliveryConfig nxtDeliveryConfig);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}