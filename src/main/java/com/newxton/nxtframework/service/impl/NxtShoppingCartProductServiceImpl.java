package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.dao.NxtShoppingCartProductDao;
import com.newxton.nxtframework.entity.NxtShoppingCartProduct;
import com.newxton.nxtframework.service.NxtShoppingCartProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtShoppingCartProduct)表服务实现类
 *
 * @author makejava
 * @since 2020-11-14 21:45:47
 */
@Service("nxtShoppingCartProductService")
public class NxtShoppingCartProductServiceImpl implements NxtShoppingCartProductService {
    @Resource
    private NxtShoppingCartProductDao nxtShoppingCartProductDao;
    
    /**
     * 通过shoppingCartId、productId查询对象列表
     *
     * @param shoppingCartId 购物车主键
     * @param productId 产品主键
     * @return 对象列表
     */
	public List<NxtShoppingCartProduct> queryByShoppingCartIdProductId(Long shoppingCartId, Long productId) {
        return this.nxtShoppingCartProductDao.queryByShoppingCartIdProductId(shoppingCartId, productId);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtShoppingCartProduct queryById(Long id) {
        return this.nxtShoppingCartProductDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtShoppingCartProduct> queryAllByLimit(int offset, int limit) {
        return this.nxtShoppingCartProductDao.queryAllByLimit(offset, limit);
    }

    /**
     * 查询指定购物车内所有选中的产品
     * @param shoppingCartId
     * @return
     */
    public List<NxtShoppingCartProduct> queryAllSelectedProductByShoppingCartId(Long shoppingCartId){
        return this.nxtShoppingCartProductDao.queryAllSelectedProductByShoppingCartId(shoppingCartId);
    }

    /**
     * 查询指定购物车内所有选中的产品
     * @param shoppingCartId
     * @return
     */
    public List<NxtShoppingCartProduct> queryAllProductByShoppingCartId(Long shoppingCartId){
        return this.nxtShoppingCartProductDao.queryAllProductByShoppingCartId(shoppingCartId);
    }

    /**
     * 新增数据
     *
     * @param nxtShoppingCartProduct 实例对象
     * @return 实例对象
     */
    @Override
    public NxtShoppingCartProduct insert(NxtShoppingCartProduct nxtShoppingCartProduct) {
        this.nxtShoppingCartProductDao.insert(nxtShoppingCartProduct);
        return nxtShoppingCartProduct;
    }

    /**
     * 修改数据
     *
     * @param nxtShoppingCartProduct 实例对象
     * @return 实例对象
     */
    @Override
    public NxtShoppingCartProduct update(NxtShoppingCartProduct nxtShoppingCartProduct) {
        this.nxtShoppingCartProductDao.update(nxtShoppingCartProduct);
        return this.queryById(nxtShoppingCartProduct.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtShoppingCartProductDao.deleteById(id) > 0;
    }
}