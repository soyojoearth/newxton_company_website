package com.newxton.companywebsite.dao;

import com.newxton.companywebsite.entity.NxtContent;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (NxtContent)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-22 15:24:23
 */
public interface NxtContentDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtContent queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtContent> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtContent 实例对象
     * @return 对象列表
     */
    List<NxtContent> queryAll(NxtContent nxtContent);

    /**
     * 新增数据
     *
     * @param nxtContent 实例对象
     * @return 影响行数
     */
    int insert(NxtContent nxtContent);

    /**
     * 修改数据
     *
     * @param nxtContent 实例对象
     * @return 影响行数
     */
    int update(NxtContent nxtContent);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}