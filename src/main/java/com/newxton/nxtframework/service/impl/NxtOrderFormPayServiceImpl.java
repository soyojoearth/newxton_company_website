package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.dao.NxtOrderFormPayDao;
import com.newxton.nxtframework.entity.NxtOrderFormPay;
import com.newxton.nxtframework.service.NxtOrderFormPayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtOrderFormPay)表服务实现类
 *
 * @author makejava
 * @since 2020-11-14 21:41:53
 */
@Service("nxtOrderFormPayService")
public class NxtOrderFormPayServiceImpl implements NxtOrderFormPayService {
    @Resource
    private NxtOrderFormPayDao nxtOrderFormPayDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtOrderFormPay queryById(Long id) {
        return this.nxtOrderFormPayDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtOrderFormPay> queryAllByLimit(int offset, int limit) {
        return this.nxtOrderFormPayDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtOrderFormPay 实例对象
     * @return 实例对象
     */
    @Override
    public NxtOrderFormPay insert(NxtOrderFormPay nxtOrderFormPay) {
        this.nxtOrderFormPayDao.insert(nxtOrderFormPay);
        return nxtOrderFormPay;
    }

    /**
     * 修改数据
     *
     * @param nxtOrderFormPay 实例对象
     * @return 实例对象
     */
    @Override
    public NxtOrderFormPay update(NxtOrderFormPay nxtOrderFormPay) {
        this.nxtOrderFormPayDao.update(nxtOrderFormPay);
        return this.queryById(nxtOrderFormPay.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtOrderFormPayDao.deleteById(id) > 0;
    }
}