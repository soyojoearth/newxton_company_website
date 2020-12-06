package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.dao.NxtOrderFormRefundPictureDao;
import com.newxton.nxtframework.entity.NxtOrderFormRefundPicture;
import com.newxton.nxtframework.service.NxtOrderFormRefundPictureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtOrderFormRefundPicture)表服务实现类
 *
 * @author makejava
 * @since 2020-11-14 21:41:57
 */
@Service("nxtOrderFormRefundPictureService")
public class NxtOrderFormRefundPictureServiceImpl implements NxtOrderFormRefundPictureService {
    @Resource
    private NxtOrderFormRefundPictureDao nxtOrderFormRefundPictureDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtOrderFormRefundPicture queryById(Long id) {
        return this.nxtOrderFormRefundPictureDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtOrderFormRefundPicture> queryAllByLimit(int offset, int limit) {
        return this.nxtOrderFormRefundPictureDao.queryAllByLimit(offset, limit);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtOrderFormRefundPicture 实例对象
     * @return 对象列表
     */
    @Override
    public List<NxtOrderFormRefundPicture> queryAll(NxtOrderFormRefundPicture nxtOrderFormRefundPicture){
        return this.nxtOrderFormRefundPictureDao.queryAll(nxtOrderFormRefundPicture);
    }

    /**
     * 新增数据
     *
     * @param nxtOrderFormRefundPicture 实例对象
     * @return 实例对象
     */
    @Override
    public NxtOrderFormRefundPicture insert(NxtOrderFormRefundPicture nxtOrderFormRefundPicture) {
        this.nxtOrderFormRefundPictureDao.insert(nxtOrderFormRefundPicture);
        return nxtOrderFormRefundPicture;
    }

    /**
     * 修改数据
     *
     * @param nxtOrderFormRefundPicture 实例对象
     * @return 实例对象
     */
    @Override
    public NxtOrderFormRefundPicture update(NxtOrderFormRefundPicture nxtOrderFormRefundPicture) {
        this.nxtOrderFormRefundPictureDao.update(nxtOrderFormRefundPicture);
        return this.queryById(nxtOrderFormRefundPicture.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtOrderFormRefundPictureDao.deleteById(id) > 0;
    }
}