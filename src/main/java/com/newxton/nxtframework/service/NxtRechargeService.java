package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtRecharge;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtRecharge)表服务接口
 *
 * @author makejava
 * @since 2020-11-23 20:34:37
 */
public interface NxtRechargeService {

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
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtRecharge> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nxtRecharge 实例对象
     * @return 实例对象
     */
    NxtRecharge insert(NxtRecharge nxtRecharge);

    /**
     * 修改数据
     *
     * @param nxtRecharge 实例对象
     * @return 实例对象
     */
    NxtRecharge update(NxtRecharge nxtRecharge);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 查询某用户充值成功的总额
     * @param userId
     * @return
     */
    Long queryTotalRechargeSuccessByUserId(@Param("userId") Long userId);

}