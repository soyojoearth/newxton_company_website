package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtUserVerifiy;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (NxtUserVerifiy)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-28 11:29:48
 */
public interface NxtUserVerifiyDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtUserVerifiy queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtUserVerifiy> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtUserVerifiy 实例对象
     * @return 对象列表
     */
    List<NxtUserVerifiy> queryAll(NxtUserVerifiy nxtUserVerifiy);

    /**
     * 新增数据
     *
     * @param nxtUserVerifiy 实例对象
     * @return 影响行数
     */
    int insert(NxtUserVerifiy nxtUserVerifiy);

    /**
     * 修改数据
     *
     * @param nxtUserVerifiy 实例对象
     * @return 影响行数
     */
    int update(NxtUserVerifiy nxtUserVerifiy);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据phoneOrEmail查询最近的那个验证码
     * @param phoneOrEmail
     * @return
     */
    NxtUserVerifiy queryLastByPhoneOrEmail(@Param("phoneOrEmail") String phoneOrEmail);

}