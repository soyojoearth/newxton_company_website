package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.dao.NxtOrderFormDeliveryDao;
import com.newxton.nxtframework.entity.NxtOrderFormDelivery;
import com.newxton.nxtframework.service.NxtOrderFormDeliveryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtOrderFormDelivery)表服务实现类
 *
 * @author makejava
 * @since 2020-11-14 21:41:51
 */
@Service("nxtOrderFormDeliveryService")
public class NxtOrderFormDeliveryServiceImpl implements NxtOrderFormDeliveryService {
    @Resource
    private NxtOrderFormDeliveryDao nxtOrderFormDeliveryDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtOrderFormDelivery queryById(Long id) {
        return this.nxtOrderFormDeliveryDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtOrderFormDelivery> queryAllByLimit(int offset, int limit) {
        return this.nxtOrderFormDeliveryDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtOrderFormDelivery 实例对象
     * @return 实例对象
     */
    @Override
    public NxtOrderFormDelivery insert(NxtOrderFormDelivery nxtOrderFormDelivery) {
        this.nxtOrderFormDeliveryDao.insert(nxtOrderFormDelivery);
        return nxtOrderFormDelivery;
    }

    /**
     * 修改数据
     *
     * @param nxtOrderFormDelivery 实例对象
     * @return 实例对象
     */
    @Override
    public NxtOrderFormDelivery update(NxtOrderFormDelivery nxtOrderFormDelivery) {
        this.nxtOrderFormDeliveryDao.update(nxtOrderFormDelivery);
        return this.queryById(nxtOrderFormDelivery.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtOrderFormDeliveryDao.deleteById(id) > 0;
    }
}