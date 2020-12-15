package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.entity.NxtRecharge;
import com.newxton.nxtframework.dao.NxtRechargeDao;
import com.newxton.nxtframework.service.NxtRechargeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtRecharge)表服务实现类
 *
 * @author makejava
 * @since 2020-11-23 20:34:38
 */
@Service("nxtRechargeService")
public class NxtRechargeServiceImpl implements NxtRechargeService {
    @Resource
    private NxtRechargeDao nxtRechargeDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtRecharge queryById(Long id) {
        return this.nxtRechargeDao.queryById(id);
    }

    /**
     * 通过serialNum查询单条数据
     *
     * @param serialNum
     * @return 实例对象
     */
    @Override
    public NxtRecharge queryBySerialNum(String serialNum){
        return this.nxtRechargeDao.queryBySerialNum(serialNum);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtRecharge> queryAllByLimit(int offset, int limit) {
        return this.nxtRechargeDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtRecharge 实例对象
     * @return 实例对象
     */
    @Override
    public NxtRecharge insert(NxtRecharge nxtRecharge) {
        this.nxtRechargeDao.insert(nxtRecharge);
        return nxtRecharge;
    }

    /**
     * 修改数据
     *
     * @param nxtRecharge 实例对象
     * @return 实例对象
     */
    @Override
    public NxtRecharge update(NxtRecharge nxtRecharge) {
        this.nxtRechargeDao.update(nxtRecharge);
        return this.queryById(nxtRecharge.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtRechargeDao.deleteById(id) > 0;
    }

    /**
     * 查询某用户充值成功的总额
     * @param userId
     * @return
     */
    public Long queryTotalRechargeSuccessByUserId(@Param("userId") Long userId){
        return this.nxtRechargeDao.queryTotalRechargeSuccessByUserId(userId);
    }

}