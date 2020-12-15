package com.newxton.nxtframework.dao;

import com.newxton.nxtframework.entity.NxtRecharge;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (NxtRecharge)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-23 20:34:36
 */
public interface NxtRechargeDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtRecharge queryById(Long id);

    /**
     * 通过serialNum查询单条数据
     *
     * @param serialNum
     * @return 实例对象
     */
    NxtRecharge queryBySerialNum(String serialNum);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtRecharge> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtRecharge 实例对象
     * @return 对象列表
     */
    List<NxtRecharge> queryAll(NxtRecharge nxtRecharge);

    /**
     * 新增数据
     *
     * @param nxtRecharge 实例对象
     * @return 影响行数
     */
    int insert(NxtRecharge nxtRecharge);

    /**
     * 修改数据
     *
     * @param nxtRecharge 实例对象
     * @return 影响行数
     */
    int update(NxtRecharge nxtRecharge);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 查询某用户充值成功的总额
     * @param userId
     * @return
     */
    Long queryTotalRechargeSuccessByUserId(@Param("userId") Long userId);

}