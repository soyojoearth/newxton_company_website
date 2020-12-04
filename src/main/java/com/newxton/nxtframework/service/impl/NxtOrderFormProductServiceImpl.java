package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.entity.NxtOrderFormProduct;
import com.newxton.nxtframework.dao.NxtOrderFormProductDao;
import com.newxton.nxtframework.service.NxtOrderFormProductService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (NxtOrderFormProduct)表服务实现类
 *
 * @author makejava
 * @since 2020-11-19 13:57:33
 */
@Service("nxtOrderFormProductService")
public class NxtOrderFormProductServiceImpl implements NxtOrderFormProductService {
    @Resource
    private NxtOrderFormProductDao nxtOrderFormProductDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtOrderFormProduct queryById(Long id) {
        return this.nxtOrderFormProductDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtOrderFormProduct> queryAllByLimit(int offset, int limit) {
        return this.nxtOrderFormProductDao.queryAllByLimit(offset, limit);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtOrderFormProduct 实例对象
     * @return 对象列表
     */
    public List<NxtOrderFormProduct> queryAll(NxtOrderFormProduct nxtOrderFormProduct){
        return this.nxtOrderFormProductDao.queryAll(nxtOrderFormProduct);
    }

    /**
     * 新增数据
     *
     * @param nxtOrderFormProduct 实例对象
     * @return 实例对象
     */
    @Override
    public NxtOrderFormProduct insert(NxtOrderFormProduct nxtOrderFormProduct) {
        this.nxtOrderFormProductDao.insert(nxtOrderFormProduct);
        return nxtOrderFormProduct;
    }

    /**
     * 修改数据
     *
     * @param nxtOrderFormProduct 实例对象
     * @return 实例对象
     */
    @Override
    public NxtOrderFormProduct update(NxtOrderFormProduct nxtOrderFormProduct) {
        this.nxtOrderFormProductDao.update(nxtOrderFormProduct);
        return this.queryById(nxtOrderFormProduct.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtOrderFormProductDao.deleteById(id) > 0;
    }

    /**
     * 根据orderFormId列表查询所有
     * @param idList
     * @return
     */
    public List<NxtOrderFormProduct> selectAllByOrderFormIdSet(@Param("idList") List<Long> idList){
        if (idList.size() == 0){
            return new ArrayList<>();
        }
        return this.nxtOrderFormProductDao.selectAllByOrderFormIdSet(idList);
    }

    /**
     * 根据Id列表查询所有
     * @param idList
     * @return
     */
    public List<NxtOrderFormProduct> selectAllByIdSet(@Param("idList") List<Long> idList){
        if (idList.size()==0){
            return new ArrayList<>();
        }
        return this.nxtOrderFormProductDao.selectAllByIdSet(idList);
    }

    /**
     * 根据ProductId列表查询
     * @param offset
     * @param limit
     * @param productId
     * @return
     */
    public List<NxtOrderFormProduct> queryAllByProductIdLimit(@Param("offset") Long offset, @Param("limit") Long limit, @Param("productId") Long productId){
        return this.nxtOrderFormProductDao.queryAllByProductIdLimit(offset, limit, productId);
    }

}