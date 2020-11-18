package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtShoppingCart;
import java.util.List;

/**
 * (NxtShoppingCart)表服务接口
 *
 * @author makejava
 * @since 2020-11-14 21:45:46
 */
public interface NxtShoppingCartService {
	
	/**
     * 通过userId查询
     *
     * @param userId 用户id
     * @return 实例对象
     */
	NxtShoppingCart queryByUserId(Long userId);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtShoppingCart queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtShoppingCart> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nxtShoppingCart 实例对象
     * @return 实例对象
     */
    NxtShoppingCart insert(NxtShoppingCart nxtShoppingCart);

    /**
     * 修改数据
     *
     * @param nxtShoppingCart 实例对象
     * @return 实例对象
     */
    NxtShoppingCart update(NxtShoppingCart nxtShoppingCart);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}