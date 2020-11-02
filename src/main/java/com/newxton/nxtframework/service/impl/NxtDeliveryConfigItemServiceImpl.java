package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.entity.NxtDeliveryConfigItem;
import com.newxton.nxtframework.dao.NxtDeliveryConfigItemDao;
import com.newxton.nxtframework.service.NxtDeliveryConfigItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtDeliveryConfigItem)表服务实现类
 *
 * @author makejava
 * @since 2020-11-02 19:03:31
 */
@Service("nxtDeliveryConfigItemService")
public class NxtDeliveryConfigItemServiceImpl implements NxtDeliveryConfigItemService {
    @Resource
    private NxtDeliveryConfigItemDao nxtDeliveryConfigItemDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtDeliveryConfigItem queryById(Long id) {
        return this.nxtDeliveryConfigItemDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtDeliveryConfigItem> queryAllByLimit(int offset, int limit) {
        return this.nxtDeliveryConfigItemDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtDeliveryConfigItem 实例对象
     * @return 实例对象
     */
    @Override
    public NxtDeliveryConfigItem insert(NxtDeliveryConfigItem nxtDeliveryConfigItem) {
        this.nxtDeliveryConfigItemDao.insert(nxtDeliveryConfigItem);
        return nxtDeliveryConfigItem;
    }

    /**
     * 修改数据
     *
     * @param nxtDeliveryConfigItem 实例对象
     * @return 实例对象
     */
    @Override
    public NxtDeliveryConfigItem update(NxtDeliveryConfigItem nxtDeliveryConfigItem) {
        this.nxtDeliveryConfigItemDao.update(nxtDeliveryConfigItem);
        return this.queryById(nxtDeliveryConfigItem.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtDeliveryConfigItemDao.deleteById(id) > 0;
    }
}