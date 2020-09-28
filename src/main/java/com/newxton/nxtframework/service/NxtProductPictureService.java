package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtProductPicture;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtProductPicture)表服务接口
 *
 * @author makejava
 * @since 2020-08-03 10:22:38
 */
public interface NxtProductPictureService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtProductPicture queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtProductPicture> queryAllByLimit(int offset, int limit);

    /**
     * 查询指定多个类型数据
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtProductPicture> selectByProductIdSet(@Param("offset") int offset, @Param("limit") int limit,
                                                 @Param("productIdList") List<Long> productIdList);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtProductPicture 实例对象
     * @return 对象列表
     */
    List<NxtProductPicture> queryAll(NxtProductPicture nxtProductPicture);

    /**
     * 新增数据
     *
     * @param nxtProductPicture 实例对象
     * @return 实例对象
     */
    NxtProductPicture insert(NxtProductPicture nxtProductPicture);

    /**
     * 修改数据
     *
     * @param nxtProductPicture 实例对象
     * @return 实例对象
     */
    NxtProductPicture update(NxtProductPicture nxtProductPicture);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}