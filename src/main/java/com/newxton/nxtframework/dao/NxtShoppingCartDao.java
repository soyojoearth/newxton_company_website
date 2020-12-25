package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtShoppingCart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtShoppingCart)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-14 21:45:46
 */
public interface NxtShoppingCartDao {
	
	/**
     * 通过token查询
     *
     * @param token 匿名用户token
     * @return 实例对象
     */
	NxtShoppingCart queryByToken(String token);
	
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
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<NxtShoppingCart> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtShoppingCart 实例对象
     * @return 对象列表
     */
    List<NxtShoppingCart> queryAll(NxtShoppingCart nxtShoppingCart);

    /**
     * 新增数据
     *
     * @param nxtShoppingCart 实例对象
     * @return 影响行数
     */
    int insert(NxtShoppingCart nxtShoppingCart);

    /**
     * 修改数据
     *
     * @param nxtShoppingCart 实例对象
     * @return 影响行数
     */
    int update(NxtShoppingCart nxtShoppingCart);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}