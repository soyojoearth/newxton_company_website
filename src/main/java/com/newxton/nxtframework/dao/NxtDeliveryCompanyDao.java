package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtDeliveryCompany;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (NxtDeliveryCompany)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-02 19:03:30
 */
public interface NxtDeliveryCompanyDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtDeliveryCompany queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtDeliveryCompany> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtDeliveryCompany 实例对象
     * @return 对象列表
     */
    List<NxtDeliveryCompany> queryAll(NxtDeliveryCompany nxtDeliveryCompany);

    /**
     * 新增数据
     *
     * @param nxtDeliveryCompany 实例对象
     * @return 影响行数
     */
    int insert(NxtDeliveryCompany nxtDeliveryCompany);

    /**
     * 修改数据
     *
     * @param nxtDeliveryCompany 实例对象
     * @return 影响行数
     */
    int update(NxtDeliveryCompany nxtDeliveryCompany);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}