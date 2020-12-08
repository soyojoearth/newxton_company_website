package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.entity.NxtOrderFormRefundDelivery;
import com.newxton.nxtframework.dao.NxtOrderFormRefundDeliveryDao;
import com.newxton.nxtframework.service.NxtOrderFormRefundDeliveryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtOrderFormRefundDelivery)表服务实现类
 *
 * @author makejava
 * @since 2020-12-08 15:39:40
 */
@Service("nxtOrderFormRefundDeliveryService")
public class NxtOrderFormRefundDeliveryServiceImpl implements NxtOrderFormRefundDeliveryService {
    @Resource
    private NxtOrderFormRefundDeliveryDao nxtOrderFormRefundDeliveryDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtOrderFormRefundDelivery queryById(Long id) {
        return this.nxtOrderFormRefundDeliveryDao.queryById(id);
    }

    /**
     * 通过orderFormRefundId查询单条数据
     *
     * @param orderFormRefundId
     * @return 实例对象
     */
    @Override
    public NxtOrderFormRefundDelivery queryByOrderFormRefundId(Long orderFormRefundId){
        return this.nxtOrderFormRefundDeliveryDao.queryByOrderFormRefundId(orderFormRefundId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtOrderFormRefundDelivery> queryAllByLimit(int offset, int limit) {
        return this.nxtOrderFormRefundDeliveryDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtOrderFormRefundDelivery 实例对象
     * @return 实例对象
     */
    @Override
    public NxtOrderFormRefundDelivery insert(NxtOrderFormRefundDelivery nxtOrderFormRefundDelivery) {
        this.nxtOrderFormRefundDeliveryDao.insert(nxtOrderFormRefundDelivery);
        return nxtOrderFormRefundDelivery;
    }

    /**
     * 修改数据
     *
     * @param nxtOrderFormRefundDelivery 实例对象
     * @return 实例对象
     */
    @Override
    public NxtOrderFormRefundDelivery update(NxtOrderFormRefundDelivery nxtOrderFormRefundDelivery) {
        this.nxtOrderFormRefundDeliveryDao.update(nxtOrderFormRefundDelivery);
        return this.queryById(nxtOrderFormRefundDelivery.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtOrderFormRefundDeliveryDao.deleteById(id) > 0;
    }
}