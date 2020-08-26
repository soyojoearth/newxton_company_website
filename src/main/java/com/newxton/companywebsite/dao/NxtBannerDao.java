package com.newxton.companywebsite.dao;

import com.newxton.companywebsite.entity.NxtBanner;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (NxtBanner)表数据库访问层
 *
 * @author makejava
 * @since 2020-08-26 16:48:10
 */
public interface NxtBannerDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtBanner queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtBanner> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtBanner 实例对象
     * @return 对象列表
     */
    List<NxtBanner> queryAll(NxtBanner nxtBanner);

    /**
     * 新增数据
     *
     * @param nxtBanner 实例对象
     * @return 影响行数
     */
    int insert(NxtBanner nxtBanner);

    /**
     * 修改数据
     *
     * @param nxtBanner 实例对象
     * @return 影响行数
     */
    int update(NxtBanner nxtBanner);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}