package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtDeliveryConfigItem;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (NxtDeliveryConfigItem)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-02 19:03:31
 */
public interface NxtDeliveryConfigItemDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtDeliveryConfigItem queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtDeliveryConfigItem> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtDeliveryConfigItem 实例对象
     * @return 对象列表
     */
    List<NxtDeliveryConfigItem> queryAll(NxtDeliveryConfigItem nxtDeliveryConfigItem);

    /**
     * 新增数据
     *
     * @param nxtDeliveryConfigItem 实例对象
     * @return 影响行数
     */
    int insert(NxtDeliveryConfigItem nxtDeliveryConfigItem);

    /**
     * 修改数据
     *
     * @param nxtDeliveryConfigItem 实例对象
     * @return 影响行数
     */
    int update(NxtDeliveryConfigItem nxtDeliveryConfigItem);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 删除指定多个类型数据
     * @return 对象列表
     */
    void deleteByIdSet(@Param("idList") List<Long> idList);

}