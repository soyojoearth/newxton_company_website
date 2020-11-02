package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtDeliveryConfigItem;
import java.util.List;

/**
 * (NxtDeliveryConfigItem)表服务接口
 *
 * @author makejava
 * @since 2020-11-02 19:03:31
 */
public interface NxtDeliveryConfigItemService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtDeliveryConfigItem queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtDeliveryConfigItem> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nxtDeliveryConfigItem 实例对象
     * @return 实例对象
     */
    NxtDeliveryConfigItem insert(NxtDeliveryConfigItem nxtDeliveryConfigItem);

    /**
     * 修改数据
     *
     * @param nxtDeliveryConfigItem 实例对象
     * @return 实例对象
     */
    NxtDeliveryConfigItem update(NxtDeliveryConfigItem nxtDeliveryConfigItem);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}