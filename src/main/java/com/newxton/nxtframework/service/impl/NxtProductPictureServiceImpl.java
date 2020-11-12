package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.dao.NxtProductPictureDao;
import com.newxton.nxtframework.entity.NxtProductPicture;
import com.newxton.nxtframework.service.NxtProductPictureService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtProductPicture)表服务实现类
 *
 * @author makejava
 * @since 2020-08-03 10:22:38
 */
@Service("nxtProductPictureService")
public class NxtProductPictureServiceImpl implements NxtProductPictureService {
    @Resource
    private NxtProductPictureDao nxtProductPictureDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtProductPicture queryById(Long id) {
        return this.nxtProductPictureDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtProductPicture> queryAllByLimit(int offset, int limit) {
        return this.nxtProductPictureDao.queryAllByLimit(offset, limit);
    }

    /**
     * 查询指定多个类型数据
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    public List<NxtProductPicture> selectByProductIdSet(@Param("offset") int offset, @Param("limit") int limit,
                                                  @Param("productIdList") List<Long> productIdList){
        return this.nxtProductPictureDao.selectByProductIdSet(offset, limit, productIdList);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtProductPicture 实例对象
     * @return 对象列表
     */
    public List<NxtProductPicture> queryAll(NxtProductPicture nxtProductPicture){
        return this.nxtProductPictureDao.queryAll(nxtProductPicture);
    }

    /**
     * 新增数据
     *
     * @param nxtProductPicture 实例对象
     * @return 实例对象
     */
    @Override
    public NxtProductPicture insert(NxtProductPicture nxtProductPicture) {
        this.nxtProductPictureDao.insert(nxtProductPicture);
        return nxtProductPicture;
    }

    /**
     * 修改数据
     *
     * @param nxtProductPicture 实例对象
     * @return 实例对象
     */
    @Override
    public NxtProductPicture update(NxtProductPicture nxtProductPicture) {
        this.nxtProductPictureDao.update(nxtProductPicture);
        return this.queryById(nxtProductPicture.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtProductPictureDao.deleteById(id) > 0;
    }
}