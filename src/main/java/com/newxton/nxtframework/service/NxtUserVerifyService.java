package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtUserVerify;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtUserVerify)表服务接口
 *
 * @author makejava
 * @since 2020-11-28 11:29:49
 */
public interface NxtUserVerifyService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtUserVerify queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtUserVerify> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nxtUserVerify 实例对象
     * @return 实例对象
     */
    NxtUserVerify insert(NxtUserVerify nxtUserVerify);

    /**
     * 修改数据
     *
     * @param nxtUserVerify 实例对象
     * @return 实例对象
     */
    NxtUserVerify update(NxtUserVerify nxtUserVerify);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 根据phoneOrEmail查询最近的那个验证码
     * @param phoneOrEmail
     * @return
     */
    NxtUserVerify queryLastByPhoneOrEmail(@Param("phoneOrEmail") String phoneOrEmail);

}