package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.entity.NxtProductBrand;
import com.newxton.nxtframework.dao.NxtProductBrandDao;
import com.newxton.nxtframework.service.NxtProductBrandService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtProductBrand)表服务实现类
 *
 * @author makejava
 * @since 2020-11-02 19:04:21
 */
@Service("nxtProductBrandService")
public class NxtProductBrandServiceImpl implements NxtProductBrandService {
    @Resource
    private NxtProductBrandDao nxtProductBrandDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtProductBrand queryById(Long id) {
        return this.nxtProductBrandDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtProductBrand> queryAllByLimit(int offset, int limit) {
        return this.nxtProductBrandDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtProductBrand 实例对象
     * @return 实例对象
     */
    @Override
    public NxtProductBrand insert(NxtProductBrand nxtProductBrand) {
        this.nxtProductBrandDao.insert(nxtProductBrand);
        return nxtProductBrand;
    }

    /**
     * 修改数据
     *
     * @param nxtProductBrand 实例对象
     * @return 实例对象
     */
    @Override
    public NxtProductBrand update(NxtProductBrand nxtProductBrand) {
        this.nxtProductBrandDao.update(nxtProductBrand);
        return this.queryById(nxtProductBrand.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtProductBrandDao.deleteById(id) > 0;
    }
}