package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtReviewsPicture;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtReviewsPicture)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-14 21:45:46
 */
public interface NxtReviewsPictureDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtReviewsPicture queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtReviewsPicture> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtReviewsPicture 实例对象
     * @return 对象列表
     */
    List<NxtReviewsPicture> queryAll(NxtReviewsPicture nxtReviewsPicture);

    /**
     * 新增数据
     *
     * @param nxtReviewsPicture 实例对象
     * @return 影响行数
     */
    int insert(NxtReviewsPicture nxtReviewsPicture);


    /**
     * 修改数据
     *
     * @param nxtReviewsPicture 实例对象
     * @return 影响行数
     */
    int update(NxtReviewsPicture nxtReviewsPicture);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据reviewsId列表批量查询
     * @param offset
     * @param limit
     * @param idList
     * @return
     */
    List<NxtReviewsPicture> selectByReviewsIdSet(@Param("offset") int offset, @Param("limit") int limit, @Param("idList") List<Long> idList);

    /**
     * 通过uploadFileId查询单条数据
     *
     * @param uploadFileId
     * @return 实例对象
     */
    NxtReviewsPicture queryByUploadFileId(@Param("uploadFileId") Long uploadFileId);

}