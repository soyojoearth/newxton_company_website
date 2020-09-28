package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtProductSkuValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtProductSkuValue)表数据库访问层
 *
 * @author makejava
 * @since 2020-08-26 09:16:41
 */
public interface NxtProductSkuValueDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtProductSkuValue queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtProductSkuValue> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


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
     * @return 影响行数
     */
    int insert(NxtProductSkuValue nxtProductSkuValue);

    /**
     * 修改数据
     *
     * @param nxtProductSkuValue 实例对象
     * @return 影响行数
     */
    int update(NxtProductSkuValue nxtProductSkuValue);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}