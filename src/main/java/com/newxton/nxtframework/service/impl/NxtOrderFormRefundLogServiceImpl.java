package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.dao.NxtOrderFormRefundLogDao;
import com.newxton.nxtframework.entity.NxtOrderFormRefundLog;
import com.newxton.nxtframework.service.NxtOrderFormRefundLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtOrderFormRefundLog)表服务实现类
 *
 * @author makejava
 * @since 2020-11-14 21:41:56
 */
@Service("nxtOrderFormRefundLogService")
public class NxtOrderFormRefundLogServiceImpl implements NxtOrderFormRefundLogService {
    @Resource
    private NxtOrderFormRefundLogDao nxtOrderFormRefundLogDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtOrderFormRefundLog queryById(Long id) {
        return this.nxtOrderFormRefundLogDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtOrderFormRefundLog> queryAllByLimit(int offset, int limit) {
        return this.nxtOrderFormRefundLogDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtOrderFormRefundLog 实例对象
     * @return 实例对象
     */
    @Override
    public NxtOrderFormRefundLog insert(NxtOrderFormRefundLog nxtOrderFormRefundLog) {
        this.nxtOrderFormRefundLogDao.insert(nxtOrderFormRefundLog);
        return nxtOrderFormRefundLog;
    }

    /**
     * 修改数据
     *
     * @param nxtOrderFormRefundLog 实例对象
     * @return 实例对象
     */
    @Override
    public NxtOrderFormRefundLog update(NxtOrderFormRefundLog nxtOrderFormRefundLog) {
        this.nxtOrderFormRefundLogDao.update(nxtOrderFormRefundLog);
        return this.queryById(nxtOrderFormRefundLog.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtOrderFormRefundLogDao.deleteById(id) > 0;
    }
}