package com.newxton.companywebsite.service.impl;

import com.newxton.companywebsite.entity.NxtProductSku;
import com.newxton.companywebsite.dao.NxtProductSkuDao;
import com.newxton.companywebsite.service.NxtProductSkuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtProductSku)表服务实现类
 *
 * @author makejava
 * @since 2020-08-26 09:16:25
 */
@Service("nxtProductSkuService")
public class NxtProductSkuServiceImpl implements NxtProductSkuService {
    @Resource
    private NxtProductSkuDao nxtProductSkuDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtProductSku queryById(Long id) {
        return this.nxtProductSkuDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtProductSku> queryAllByLimit(int offset, int limit) {
        return this.nxtProductSkuDao.queryAllByLimit(offset, limit);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtProductSku 实例对象
     * @return 对象列表
     */
    public List<NxtProductSku> queryAll(NxtProductSku nxtProductSku){
        return this.nxtProductSkuDao.queryAll(nxtProductSku);
    }

    /**
     * 新增数据
     *
     * @param nxtProductSku 实例对象
     * @return 实例对象
     */
    @Override
    public NxtProductSku insert(NxtProductSku nxtProductSku) {
        this.nxtProductSkuDao.insert(nxtProductSku);
        return nxtProductSku;
    }

    /**
     * 修改数据
     *
     * @param nxtProductSku 实例对象
     * @return 实例对象
     */
    @Override
    public NxtProductSku update(NxtProductSku nxtProductSku) {
        this.nxtProductSkuDao.update(nxtProductSku);
        return this.queryById(nxtProductSku.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtProductSkuDao.deleteById(id) > 0;
    }
}