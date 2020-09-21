package com.newxton.supercompanyapi.dao;

import com.newxton.supercompanyapi.entity.NxtProductSku;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (NxtProductSku)表数据库访问层
 *
 * @author makejava
 * @since 2020-08-26 09:16:25
 */
public interface NxtProductSkuDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtProductSku queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtProductSku> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtProductSku 实例对象
     * @return 对象列表
     */
    List<NxtProductSku> queryAll(NxtProductSku nxtProductSku);

    /**
     * 新增数据
     *
     * @param nxtProductSku 实例对象
     * @return 影响行数
     */
    int insert(NxtProductSku nxtProductSku);

    /**
     * 修改数据
     *
     * @param nxtProductSku 实例对象
     * @return 影响行数
     */
    int update(NxtProductSku nxtProductSku);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}