package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.entity.NxtCronjob;
import com.newxton.nxtframework.dao.NxtCronjobDao;
import com.newxton.nxtframework.service.NxtCronjobService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtCronjob)表服务实现类
 *
 * @author makejava
 * @since 2020-10-18 16:11:37
 */
@Service("nxtCronjobService")
public class NxtCronjobServiceImpl implements NxtCronjobService {
    @Resource
    private NxtCronjobDao nxtCronjobDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtCronjob queryById(Long id) {
        return this.nxtCronjobDao.queryById(id);
    }

    /**
     * 通过Key查询单条数据
     *
     * @param key 唯一标识符
     * @return 实例对象
     */
    public NxtCronjob queryByKey(String key){
        return this.nxtCronjobDao.queryByKey(key);
    };

    /**
     * 通过Key查询单条数据(带悲观锁)
     *
     * @param key 唯一标识符
     * @return 实例对象
     */
    public NxtCronjob queryByKeyForUpdate(String key){
        return this.nxtCronjobDao.queryByKeyForUpdate(key);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtCronjob> queryAllByLimit(int offset, int limit) {
        return this.nxtCronjobDao.queryAllByLimit(offset, limit);
    }

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    public List<NxtCronjob> queryAll(NxtCronjob nxtCronjob){
        return this.nxtCronjobDao.queryAll(nxtCronjob);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtCronjob 实例对象
     * @return 对象列表
     */
    public List<NxtCronjob> queryAllGreaterThanStatusDateline(NxtCronjob nxtCronjob){
        return this.nxtCronjobDao.queryAllGreaterThanStatusDateline(nxtCronjob);
    }

    /**
     * 新增数据
     *
     * @param nxtCronjob 实例对象
     * @return 实例对象
     */
    @Override
    public NxtCronjob insert(NxtCronjob nxtCronjob) {
        this.nxtCronjobDao.insert(nxtCronjob);
        return nxtCronjob;
    }

    /**
     * 修改数据
     *
     * @param nxtCronjob 实例对象
     * @return 实例对象
     */
    @Override
    public NxtCronjob update(NxtCronjob nxtCronjob) {
        this.nxtCronjobDao.update(nxtCronjob);
        return this.queryById(nxtCronjob.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtCronjobDao.deleteById(id) > 0;
    }
}