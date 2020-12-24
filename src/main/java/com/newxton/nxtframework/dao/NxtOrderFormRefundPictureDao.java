package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtOrderFormRefundPicture;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtOrderFormRefundPicture)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-14 21:41:56
 */
public interface NxtOrderFormRefundPictureDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtOrderFormRefundPicture queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtOrderFormRefundPicture> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtOrderFormRefundPicture 实例对象
     * @return 对象列表
     */
    List<NxtOrderFormRefundPicture> queryAll(NxtOrderFormRefundPicture nxtOrderFormRefundPicture);

    /**
     * 新增数据
     *
     * @param nxtOrderFormRefundPicture 实例对象
     * @return 影响行数
     */
    int insert(NxtOrderFormRefundPicture nxtOrderFormRefundPicture);

    /**
     * 修改数据
     *
     * @param nxtOrderFormRefundPicture 实例对象
     * @return 影响行数
     */
    int update(NxtOrderFormRefundPicture nxtOrderFormRefundPicture);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}