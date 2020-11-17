package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtTransaction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtTransaction)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-14 21:45:50
 */
public interface NxtTransactionDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtTransaction queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtTransaction> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtTransaction 实例对象
     * @return 对象列表
     */
    List<NxtTransaction> queryAll(NxtTransaction nxtTransaction);

    /**
     * 新增数据
     *
     * @param nxtTransaction 实例对象
     * @return 影响行数
     */
    int insert(NxtTransaction nxtTransaction);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<NxtTransaction> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<NxtTransaction> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<NxtTransaction> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<NxtTransaction> entities);

    /**
     * 修改数据
     *
     * @param nxtTransaction 实例对象
     * @return 影响行数
     */
    int update(NxtTransaction nxtTransaction);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}