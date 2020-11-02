package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.entity.NxtDeliveryConfig;
import com.newxton.nxtframework.dao.NxtDeliveryConfigDao;
import com.newxton.nxtframework.service.NxtDeliveryConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtDeliveryConfig)表服务实现类
 *
 * @author makejava
 * @since 2020-11-02 19:03:31
 */
@Service("nxtDeliveryConfigService")
public class NxtDeliveryConfigServiceImpl implements NxtDeliveryConfigService {
    @Resource
    private NxtDeliveryConfigDao nxtDeliveryConfigDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtDeliveryConfig queryById(Long id) {
        return this.nxtDeliveryConfigDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtDeliveryConfig> queryAllByLimit(int offset, int limit) {
        return this.nxtDeliveryConfigDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtDeliveryConfig 实例对象
     * @return 实例对象
     */
    @Override
    public NxtDeliveryConfig insert(NxtDeliveryConfig nxtDeliveryConfig) {
        this.nxtDeliveryConfigDao.insert(nxtDeliveryConfig);
        return nxtDeliveryConfig;
    }

    /**
     * 修改数据
     *
     * @param nxtDeliveryConfig 实例对象
     * @return 实例对象
     */
    @Override
    public NxtDeliveryConfig update(NxtDeliveryConfig nxtDeliveryConfig) {
        this.nxtDeliveryConfigDao.update(nxtDeliveryConfig);
        return this.queryById(nxtDeliveryConfig.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtDeliveryConfigDao.deleteById(id) > 0;
    }
}