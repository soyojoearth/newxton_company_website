package com.newxton.companywebsite.service.impl;

import com.newxton.companywebsite.entity.NxtProductPicture;
import com.newxton.companywebsite.dao.NxtProductPictureDao;
import com.newxton.companywebsite.service.NxtProductPictureService;
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