package com.newxton.companywebsite.dao;

import com.newxton.companywebsite.entity.NxtContent;
import com.newxton.companywebsite.entity.NxtUploadfile;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (NxtUploadfile)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-23 09:25:37
 */
public interface NxtUploadfileDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtUploadfile queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtUploadfile> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 查询指定多个类型数据
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtUploadfile> selectByIdSet(@Param("offset") int offset, @Param("limit") int limit,
                                                  @Param("idList") List<Long> idList);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtUploadfile 实例对象
     * @return 对象列表
     */
    List<NxtUploadfile> queryAll(NxtUploadfile nxtUploadfile);

    /**
     * 通过实体作为筛选条件查询Count
     *
     * @param nxtUploadfile 实例对象
     * @return 对象列表
     */
    Long queryCount(NxtUploadfile nxtUploadfile);

    /**
     * 新增数据
     *
     * @param nxtUploadfile 实例对象
     * @return 影响行数
     */
    int insert(NxtUploadfile nxtUploadfile);

    /**
     * 修改数据
     *
     * @param nxtUploadfile 实例对象
     * @return 影响行数
     */
    int update(NxtUploadfile nxtUploadfile);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}