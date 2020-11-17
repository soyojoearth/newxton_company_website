package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.dao.NxtUserAddressDao;
import com.newxton.nxtframework.entity.NxtUserAddress;
import com.newxton.nxtframework.service.NxtUserAddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtUserAddress)表服务实现类
 *
 * @author makejava
 * @since 2020-11-14 21:45:49
 */
@Service("nxtUserAddressService")
public class NxtUserAddressServiceImpl implements NxtUserAddressService {
    @Resource
    private NxtUserAddressDao nxtUserAddressDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtUserAddress queryById(Long id) {
        return this.nxtUserAddressDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtUserAddress> queryAllByLimit(int offset, int limit) {
        return this.nxtUserAddressDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtUserAddress 实例对象
     * @return 实例对象
     */
    @Override
    public NxtUserAddress insert(NxtUserAddress nxtUserAddress) {
        this.nxtUserAddressDao.insert(nxtUserAddress);
        return nxtUserAddress;
    }

    /**
     * 修改数据
     *
     * @param nxtUserAddress 实例对象
     * @return 实例对象
     */
    @Override
    public NxtUserAddress update(NxtUserAddress nxtUserAddress) {
        this.nxtUserAddressDao.update(nxtUserAddress);
        return this.queryById(nxtUserAddress.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtUserAddressDao.deleteById(id) > 0;
    }
}