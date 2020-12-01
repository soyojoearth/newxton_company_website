package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.dao.NxtCommissionTransferInDao;
import com.newxton.nxtframework.entity.NxtCommissionTransferIn;
import com.newxton.nxtframework.service.NxtCommissionTransferInService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtCommissionTransferIn)表服务实现类
 *
 * @author makejava
 * @since 2020-11-14 21:44:38
 */
@Service("nxtCommissionTransferInService")
public class NxtCommissionTransferInServiceImpl implements NxtCommissionTransferInService {
    @Resource
    private NxtCommissionTransferInDao nxtCommissionTransferInDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtCommissionTransferIn queryById(Long id) {
        return this.nxtCommissionTransferInDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtCommissionTransferIn> queryAllByLimit(int offset, int limit) {
        return this.nxtCommissionTransferInDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtCommissionTransferIn 实例对象
     * @return 实例对象
     */
    @Override
    public NxtCommissionTransferIn insert(NxtCommissionTransferIn nxtCommissionTransferIn) {
        this.nxtCommissionTransferInDao.insert(nxtCommissionTransferIn);
        return nxtCommissionTransferIn;
    }

    /**
     * 修改数据
     *
     * @param nxtCommissionTransferIn 实例对象
     * @return 实例对象
     */
    @Override
    public NxtCommissionTransferIn update(NxtCommissionTransferIn nxtCommissionTransferIn) {
        this.nxtCommissionTransferInDao.update(nxtCommissionTransferIn);
        return this.queryById(nxtCommissionTransferIn.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtCommissionTransferInDao.deleteById(id) > 0;
    }

    /**
     * 根据UserId查询结转中的收益统计（已提交结转申请，等待审核）
     * @param userId
     * @return
     */
    public Long querySumIsTransferingByUserId(@Param("userId") Long userId){
        return this.nxtCommissionTransferInDao.querySumIsTransferingByUserId(userId);
    }

    /**
     * 根据UserId查询结转被拒绝的的收益统计（已提交结转申请，审核被拒绝）
     * @param userId
     * @return
     */
    public Long querySumIsTransferRejectedByUserId(@Param("userId") Long userId){
        return this.nxtCommissionTransferInDao.querySumIsTransferRejectedByUserId(userId);
    }

}