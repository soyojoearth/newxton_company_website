package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtProductBrand;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (NxtProductBrand)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-02 19:04:21
 */
public interface NxtProductBrandDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtProductBrand queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtProductBrand> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 查询所有数据(按名字排序)
     *
     * @return 对象列表
     */
    List<NxtProductBrand> queryAllOrderByNameASC();

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtProductBrand 实例对象
     * @return 对象列表
     */
    List<NxtProductBrand> queryAll(NxtProductBrand nxtProductBrand);

    /**
     * 新增数据
     *
     * @param nxtProductBrand 实例对象
     * @return 影响行数
     */
    int insert(NxtProductBrand nxtProductBrand);

    /**
     * 修改数据
     *
     * @param nxtProductBrand 实例对象
     * @return 影响行数
     */
    int update(NxtProductBrand nxtProductBrand);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}