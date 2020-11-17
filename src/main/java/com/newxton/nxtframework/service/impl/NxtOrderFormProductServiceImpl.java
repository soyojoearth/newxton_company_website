package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.dao.NxtOrderFormProductDao;
import com.newxton.nxtframework.entity.NxtOrderFormProduct;
import com.newxton.nxtframework.service.NxtOrderFormProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtOrderFormProduct)表服务实现类
 *
 * @author makejava
 * @since 2020-11-14 21:41:54
 */
@Service("nxtOrderFormProductService")
public class NxtOrderFormProductServiceImpl implements NxtOrderFormProductService {
    @Resource
    private NxtOrderFormProductDao nxtOrderFormProductDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtOrderFormProduct queryById(Long id) {
        return this.nxtOrderFormProductDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtOrderFormProduct> queryAllByLimit(int offset, int limit) {
        return this.nxtOrderFormProductDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtOrderFormProduct 实例对象
     * @return 实例对象
     */
    @Override
    public NxtOrderFormProduct insert(NxtOrderFormProduct nxtOrderFormProduct) {
        this.nxtOrderFormProductDao.insert(nxtOrderFormProduct);
        return nxtOrderFormProduct;
    }

    /**
     * 修改数据
     *
     * @param nxtOrderFormProduct 实例对象
     * @return 实例对象
     */
    @Override
    public NxtOrderFormProduct update(NxtOrderFormProduct nxtOrderFormProduct) {
        this.nxtOrderFormProductDao.update(nxtOrderFormProduct);
        return this.queryById(nxtOrderFormProduct.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtOrderFormProductDao.deleteById(id) > 0;
    }
}