package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.dao.NxtOrderFormRefundProductDao;
import com.newxton.nxtframework.entity.NxtOrderFormRefundProduct;
import com.newxton.nxtframework.service.NxtOrderFormRefundProductService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (NxtOrderFormRefundProduct)表服务实现类
 *
 * @author makejava
 * @since 2020-11-14 21:41:58
 */
@Service("nxtOrderFormRefundProductService")
public class NxtOrderFormRefundProductServiceImpl implements NxtOrderFormRefundProductService {
    @Resource
    private NxtOrderFormRefundProductDao nxtOrderFormRefundProductDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtOrderFormRefundProduct queryById(Long id) {
        return this.nxtOrderFormRefundProductDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtOrderFormRefundProduct> queryAllByLimit(int offset, int limit) {
        return this.nxtOrderFormRefundProductDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtOrderFormRefundProduct 实例对象
     * @return 实例对象
     */
    @Override
    public NxtOrderFormRefundProduct insert(NxtOrderFormRefundProduct nxtOrderFormRefundProduct) {
        this.nxtOrderFormRefundProductDao.insert(nxtOrderFormRefundProduct);
        return nxtOrderFormRefundProduct;
    }

    /**
     * 修改数据
     *
     * @param nxtOrderFormRefundProduct 实例对象
     * @return 实例对象
     */
    @Override
    public NxtOrderFormRefundProduct update(NxtOrderFormRefundProduct nxtOrderFormRefundProduct) {
        this.nxtOrderFormRefundProductDao.update(nxtOrderFormRefundProduct);
        return this.queryById(nxtOrderFormRefundProduct.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtOrderFormRefundProductDao.deleteById(id) > 0;
    }

    /**
     * 根据退款服务单id列表查询指定行数据
     * @param idList
     * @return
     */
    public List<NxtOrderFormRefundProduct> selectAllByOrderFormRefundIdSet(@Param("idList") List<Long> idList){
        if (idList.size() == 0){
            return new ArrayList<>();
        }
        return this.nxtOrderFormRefundProductDao.selectAllByOrderFormRefundIdSet(idList);
    }

}