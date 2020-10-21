package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtAclAction;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (NxtAclAction)表数据库访问层
 *
 * @author makejava
 * @since 2020-10-21 11:09:39
 */
public interface NxtAclActionDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtAclAction queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtAclAction> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtAclAction 实例对象
     * @return 对象列表
     */
    List<NxtAclAction> queryAll(NxtAclAction nxtAclAction);

    /**
     * 新增数据
     *
     * @param nxtAclAction 实例对象
     * @return 影响行数
     */
    int insert(NxtAclAction nxtAclAction);

    /**
     * 修改数据
     *
     * @param nxtAclAction 实例对象
     * @return 影响行数
     */
    int update(NxtAclAction nxtAclAction);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}