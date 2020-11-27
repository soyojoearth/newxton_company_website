package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.dao.NxtReviewsPictureDao;
import com.newxton.nxtframework.entity.NxtReviewsPicture;
import com.newxton.nxtframework.service.NxtReviewsPictureService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (NxtReviewsPicture)表服务实现类
 *
 * @author makejava
 * @since 2020-11-14 21:45:46
 */
@Service("nxtReviewsPictureService")
public class NxtReviewsPictureServiceImpl implements NxtReviewsPictureService {
    @Resource
    private NxtReviewsPictureDao nxtReviewsPictureDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtReviewsPicture queryById(Long id) {
        return this.nxtReviewsPictureDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtReviewsPicture> queryAllByLimit(int offset, int limit) {
        return this.nxtReviewsPictureDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtReviewsPicture 实例对象
     * @return 实例对象
     */
    @Override
    public NxtReviewsPicture insert(NxtReviewsPicture nxtReviewsPicture) {
        this.nxtReviewsPictureDao.insert(nxtReviewsPicture);
        return nxtReviewsPicture;
    }

    /**
     * 修改数据
     *
     * @param nxtReviewsPicture 实例对象
     * @return 实例对象
     */
    @Override
    public NxtReviewsPicture update(NxtReviewsPicture nxtReviewsPicture) {
        this.nxtReviewsPictureDao.update(nxtReviewsPicture);
        return this.queryById(nxtReviewsPicture.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtReviewsPictureDao.deleteById(id) > 0;
    }

    /**
     * 根据reviewsId列表批量查询
     * @param offset
     * @param limit
     * @param idList
     * @return
     */
    public List<NxtReviewsPicture> selectByReviewsIdSet(@Param("offset") int offset, @Param("limit") int limit, @Param("idList") List<Long> idList){
        if (idList.size()==0){
            return new ArrayList<>();
        }
        return this.nxtReviewsPictureDao.selectByReviewsIdSet(offset,limit,idList);
    }

    /**
     * 通过uploadFileId查询单条数据
     *
     * @param uploadFileId
     * @return 实例对象
     */
    public NxtReviewsPicture queryByUploadFileId(@Param("uploadFileId") Long uploadFileId){
        return this.nxtReviewsPictureDao.queryByUploadFileId(uploadFileId);
    }

}