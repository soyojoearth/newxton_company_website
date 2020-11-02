package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtDeliveryConfigItemRegion;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (NxtDeliveryConfigItemRegion)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-02 19:03:31
 */
public interface NxtDeliveryConfigItemRegionDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtDeliveryConfigItemRegion queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtDeliveryConfigItemRegion> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtDeliveryConfigItemRegion 实例对象
     * @return 对象列表
     */
    List<NxtDeliveryConfigItemRegion> queryAll(NxtDeliveryConfigItemRegion nxtDeliveryConfigItemRegion);

    /**
     * 新增数据
     *
     * @param nxtDeliveryConfigItemRegion 实例对象
     * @return 影响行数
     */
    int insert(NxtDeliveryConfigItemRegion nxtDeliveryConfigItemRegion);

    /**
     * 修改数据
     *
     * @param nxtDeliveryConfigItemRegion 实例对象
     * @return 影响行数
     */
    int update(NxtDeliveryConfigItemRegion nxtDeliveryConfigItemRegion);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}