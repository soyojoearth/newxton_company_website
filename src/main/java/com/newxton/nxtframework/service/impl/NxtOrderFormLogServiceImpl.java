package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.dao.NxtOrderFormLogDao;
import com.newxton.nxtframework.entity.NxtOrderFormLog;
import com.newxton.nxtframework.service.NxtOrderFormLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtOrderFormLog)表服务实现类
 *
 * @author makejava
 * @since 2020-11-14 21:41:52
 */
@Service("nxtOrderFormLogService")
public class NxtOrderFormLogServiceImpl implements NxtOrderFormLogService {
    @Resource
    private NxtOrderFormLogDao nxtOrderFormLogDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtOrderFormLog queryById(Long id) {
        return this.nxtOrderFormLogDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtOrderFormLog> queryAllByLimit(int offset, int limit) {
        return this.nxtOrderFormLogDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtOrderFormLog 实例对象
     * @return 实例对象
     */
    @Override
    public NxtOrderFormLog insert(NxtOrderFormLog nxtOrderFormLog) {
        this.nxtOrderFormLogDao.insert(nxtOrderFormLog);
        return nxtOrderFormLog;
    }

    /**
     * 修改数据
     *
     * @param nxtOrderFormLog 实例对象
     * @return 实例对象
     */
    @Override
    public NxtOrderFormLog update(NxtOrderFormLog nxtOrderFormLog) {
        this.nxtOrderFormLogDao.update(nxtOrderFormLog);
        return this.queryById(nxtOrderFormLog.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtOrderFormLogDao.deleteById(id) > 0;
    }
}