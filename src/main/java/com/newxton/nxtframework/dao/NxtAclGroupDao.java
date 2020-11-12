package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtAclGroup;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (NxtAclGroup)表数据库访问层
 *
 * @author makejava
 * @since 2020-10-21 11:10:19
 */
public interface NxtAclGroupDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtAclGroup queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtAclGroup> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtAclGroup 实例对象
     * @return 对象列表
     */
    List<NxtAclGroup> queryAll(NxtAclGroup nxtAclGroup);

    /**
     * 新增数据
     *
     * @param nxtAclGroup 实例对象
     * @return 影响行数
     */
    int insert(NxtAclGroup nxtAclGroup);

    /**
     * 修改数据
     *
     * @param nxtAclGroup 实例对象
     * @return 影响行数
     */
    int update(NxtAclGroup nxtAclGroup);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}