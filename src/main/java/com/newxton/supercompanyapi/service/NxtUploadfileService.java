package com.newxton.supercompanyapi.service;

import com.newxton.supercompanyapi.entity.NxtUploadfile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtUploadfile)表服务接口
 *
 * @author makejava
 * @since 2020-07-23 09:25:37
 */
public interface NxtUploadfileService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtUploadfile queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtUploadfile> queryAllByLimit(int offset, int limit);

    /**
     * 查询指定多个类型数据
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtUploadfile> selectByIdSet(@Param("offset") int offset, @Param("limit") int limit,
                                              @Param("idList") List<Long> idList);

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
     * @return 实例对象
     */
    NxtUploadfile insert(NxtUploadfile nxtUploadfile);

    /**
     * 修改数据
     *
     * @param nxtUploadfile 实例对象
     * @return 实例对象
     */
    NxtUploadfile update(NxtUploadfile nxtUploadfile);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}