package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtProductSkuValuePriceEtc;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (NxtProductSkuValuePriceEtc)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-03 16:57:15
 */
public interface NxtProductSkuValuePriceEtcDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtProductSkuValuePriceEtc queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtProductSkuValuePriceEtc> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtProductSkuValuePriceEtc 实例对象
     * @return 对象列表
     */
    List<NxtProductSkuValuePriceEtc> queryAll(NxtProductSkuValuePriceEtc nxtProductSkuValuePriceEtc);

    /**
     * 查询指定多个类型数据
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtProductSkuValuePriceEtc> selectByValueIdSet(@Param("offset") int offset, @Param("limit") int limit,
                                                   @Param("valueIdList") List<Long> valueIdList);

    /**
     * 删除指定多个类型数据
     * @return 对象列表
     */
    List<NxtProductSkuValuePriceEtc> deleteByValueIdSet(@Param("valueIdList") List<Long> valueIdList);

    /**
     * 新增数据
     *
     * @param nxtProductSkuValuePriceEtc 实例对象
     * @return 影响行数
     */
    int insert(NxtProductSkuValuePriceEtc nxtProductSkuValuePriceEtc);

    /**
     * 修改数据
     *
     * @param nxtProductSkuValuePriceEtc 实例对象
     * @return 影响行数
     */
    int update(NxtProductSkuValuePriceEtc nxtProductSkuValuePriceEtc);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}