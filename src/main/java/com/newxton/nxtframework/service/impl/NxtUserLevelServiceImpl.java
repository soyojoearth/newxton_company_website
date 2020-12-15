package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.dao.NxtUserLevelDao;
import com.newxton.nxtframework.entity.NxtUserLevel;
import com.newxton.nxtframework.service.NxtUserLevelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtUserLevel)表服务实现类
 *
 * @author makejava
 * @since 2020-11-14 21:45:48
 */
@Service("nxtUserLevelService")
public class NxtUserLevelServiceImpl implements NxtUserLevelService {
    @Resource
    private NxtUserLevelDao nxtUserLevelDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtUserLevel queryById(Long id) {
        return this.nxtUserLevelDao.queryById(id);
    }

    /**
     * 通过num查询单条数据
     *
     * @param num
     * @return 实例对象
     */
    public NxtUserLevel queryByNum(Integer num){
        return this.nxtUserLevelDao.queryByNum(num);
    }

    /**
     * 查询levelNum最大的那个
     * @return
     */
    public NxtUserLevel queryMaxOne(){
        return this.nxtUserLevelDao.queryMaxOne();
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtUserLevel> queryAllByLimit(int offset, int limit) {
        return this.nxtUserLevelDao.queryAllByLimit(offset, limit);
    }


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtUserLevel 实例对象
     * @return 对象列表
     */
    @Override
    public List<NxtUserLevel> queryAll(NxtUserLevel nxtUserLevel){
        return this.nxtUserLevelDao.queryAll(nxtUserLevel);
    }

    /**
     * 新增数据
     *
     * @param nxtUserLevel 实例对象
     * @return 实例对象
     */
    @Override
    public NxtUserLevel insert(NxtUserLevel nxtUserLevel) {
        this.nxtUserLevelDao.insert(nxtUserLevel);
        return nxtUserLevel;
    }

    /**
     * 修改数据
     *
     * @param nxtUserLevel 实例对象
     * @return 实例对象
     */
    @Override
    public NxtUserLevel update(NxtUserLevel nxtUserLevel) {
        this.nxtUserLevelDao.update(nxtUserLevel);
        return this.queryById(nxtUserLevel.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtUserLevelDao.deleteById(id) > 0;
    }
}