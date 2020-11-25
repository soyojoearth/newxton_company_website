package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.entity.NxtOrderForm;
import com.newxton.nxtframework.dao.NxtOrderFormDao;
import com.newxton.nxtframework.service.NxtOrderFormService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (NxtOrderForm)表服务实现类
 *
 * @author makejava
 * @since 2020-11-19 11:11:07
 */
@Service("nxtOrderFormService")
public class NxtOrderFormServiceImpl implements NxtOrderFormService {
    @Resource
    private NxtOrderFormDao nxtOrderFormDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtOrderForm queryById(Long id) {
        return this.nxtOrderFormDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtOrderForm> queryAllByLimit(int offset, int limit) {
        return this.nxtOrderFormDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtOrderForm 实例对象
     * @return 实例对象
     */
    @Override
    public NxtOrderForm insert(NxtOrderForm nxtOrderForm) {
        this.nxtOrderFormDao.insert(nxtOrderForm);
        return nxtOrderForm;
    }

    /**
     * 修改数据
     *
     * @param nxtOrderForm 实例对象
     * @return 实例对象
     */
    @Override
    public NxtOrderForm update(NxtOrderForm nxtOrderForm) {
        this.nxtOrderFormDao.update(nxtOrderForm);
        return this.queryById(nxtOrderForm.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtOrderFormDao.deleteById(id) > 0;
    }

    /**
     * 用户中心，查询订单
     * @param offset
     * @param limit
     * @param userId
     * @return
     */
    public List<NxtOrderForm> queryAllByUserIdAndLimit(@Param("offset") Long offset, @Param("limit") Long limit, @Param("userId") Long userId, @Param("isPaid") Boolean isPaid, @Param("isDelivery") Boolean isDelivery, @Param("isReviews") Boolean isReviews){
        return this.nxtOrderFormDao.queryAllByUserIdAndLimit(offset,limit,userId,isPaid,isDelivery,isReviews);
    }

    /**
     * 根据id列表查询指定行数据
     * @return
     */
    public List<NxtOrderForm> selectByIdSet(@Param("idList") List<Long> idList){
        if (idList.size() == 0){
            return new ArrayList<>();
        }
        return this.nxtOrderFormDao.selectByIdSet(idList);
    }

}