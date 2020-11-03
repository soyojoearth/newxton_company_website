package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtProductSkuValuePriceEtc;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtProductSkuValuePriceEtc)表服务接口
 *
 * @author makejava
 * @since 2020-11-03 16:57:15
 */
public interface NxtProductSkuValuePriceEtcService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtProductSkuValuePriceEtc queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtProductSkuValuePriceEtc> queryAllByLimit(int offset, int limit);

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
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtProductSkuValuePriceEtc> deleteByValueIdSet(@Param("valueIdList") List<Long> valueIdList);

    /**
     * 新增数据
     *
     * @param nxtProductSkuValuePriceEtc 实例对象
     * @return 实例对象
     */
    NxtProductSkuValuePriceEtc insert(NxtProductSkuValuePriceEtc nxtProductSkuValuePriceEtc);

    /**
     * 修改数据
     *
     * @param nxtProductSkuValuePriceEtc 实例对象
     * @return 实例对象
     */
    NxtProductSkuValuePriceEtc update(NxtProductSkuValuePriceEtc nxtProductSkuValuePriceEtc);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}