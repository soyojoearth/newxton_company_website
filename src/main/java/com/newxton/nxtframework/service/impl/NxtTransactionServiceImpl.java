package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.dao.NxtTransactionDao;
import com.newxton.nxtframework.entity.NxtTransaction;
import com.newxton.nxtframework.service.NxtTransactionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtTransaction)表服务实现类
 *
 * @author makejava
 * @since 2020-11-14 21:45:51
 */
@Service("nxtTransactionService")
public class NxtTransactionServiceImpl implements NxtTransactionService {
    @Resource
    private NxtTransactionDao nxtTransactionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtTransaction queryById(Long id) {
        return this.nxtTransactionDao.queryById(id);
    }

    /**
     * 根据type、outId查询单个
     * @param type
     * @param outerId
     * @return
     */
    @Override
    public NxtTransaction queryByTypeAndOuterId(Integer type, Long outerId){
        return this.nxtTransactionDao.queryByTypeAndOuterId(type, outerId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtTransaction> queryAllByLimit(int offset, int limit) {
        return this.nxtTransactionDao.queryAllByLimit(offset, limit);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtTransaction 实例对象
     * @return 对象列表
     */
    @Override
    public List<NxtTransaction> queryAll(NxtTransaction nxtTransaction){
        return this.nxtTransactionDao.queryAll(nxtTransaction);
    }

    /**
     * 新增数据
     *
     * @param nxtTransaction 实例对象
     * @return 实例对象
     */
    @Override
    public NxtTransaction insert(NxtTransaction nxtTransaction) {
        this.nxtTransactionDao.insert(nxtTransaction);
        return nxtTransaction;
    }

    /**
     * 修改数据
     *
     * @param nxtTransaction 实例对象
     * @return 实例对象
     */
    @Override
    public NxtTransaction update(NxtTransaction nxtTransaction) {
        this.nxtTransactionDao.update(nxtTransaction);
        return this.queryById(nxtTransaction.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtTransactionDao.deleteById(id) > 0;
    }

    /**
     * 查询单个用户的余额
     * @return Long
     */
    public Long queryBalanceSumByUserId(@Param("userId") Long userId){
        return this.nxtTransactionDao.queryBalanceSumByUserId(userId);
    }

    /**
     * 查询用户余额变动明细
     * @param offset
     * @param limit
     * @param userId
     * @return
     */
    public List<NxtTransaction> queryAllByUserIdLimit(@Param("offset") Long offset, @Param("limit") Long limit, @Param("userId") Long userId){
        return this.nxtTransactionDao.queryAllByUserIdLimit(offset,limit,userId);
    }

    /**
     * 后台查询资金log列表
     * @param offset
     * @param limit
     * @param userId
     * @param type
     * @return
     */
    public List<NxtTransaction> adminQueryList(Long offset, Long limit, Long userId, Integer type){
        return this.nxtTransactionDao.adminQueryList(offset, limit, userId, type);
    }

    /**
     * 后台查询资金log列表
     * @param userId
     * @param type
     * @return
     */
    public Long adminQueryCount(Long userId, Integer type){
        return this.nxtTransactionDao.adminQueryCount(userId, type);
    }

}