package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.dao.NxtShoppingCartDao;
import com.newxton.nxtframework.entity.NxtShoppingCart;
import com.newxton.nxtframework.service.NxtShoppingCartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtShoppingCart)表服务实现类
 *
 * @author makejava
 * @since 2020-11-14 21:45:47
 */
@Service("nxtShoppingCartService")
public class NxtShoppingCartServiceImpl implements NxtShoppingCartService {
    @Resource
    private NxtShoppingCartDao nxtShoppingCartDao;
    

	/**
     * 通过token查询
     *
     * @param token 匿名用户token
     * @return 实例对象
     */
    @Override
    public NxtShoppingCart queryByToken(String token) {
        return this.nxtShoppingCartDao.queryByToken(token);
    }
	
	/**
     * 通过userId查询
     *
     * @param userId 用户id
     * @return 实例对象
     */
    @Override
    public NxtShoppingCart queryByUserId(Long userId) {
        return this.nxtShoppingCartDao.queryByUserId(userId);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtShoppingCart queryById(Long id) {
        return this.nxtShoppingCartDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtShoppingCart> queryAllByLimit(int offset, int limit) {
        return this.nxtShoppingCartDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtShoppingCart 实例对象
     * @return 实例对象
     */
    @Override
    public NxtShoppingCart insert(NxtShoppingCart nxtShoppingCart) {
        this.nxtShoppingCartDao.insert(nxtShoppingCart);
        return nxtShoppingCart;
    }

    /**
     * 修改数据
     *
     * @param nxtShoppingCart 实例对象
     * @return 实例对象
     */
    @Override
    public NxtShoppingCart update(NxtShoppingCart nxtShoppingCart) {
        this.nxtShoppingCartDao.update(nxtShoppingCart);
        return this.queryById(nxtShoppingCart.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtShoppingCartDao.deleteById(id) > 0;
    }
}