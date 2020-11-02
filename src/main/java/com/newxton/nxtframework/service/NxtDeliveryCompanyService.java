package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtDeliveryCompany;
import java.util.List;

/**
 * (NxtDeliveryCompany)表服务接口
 *
 * @author makejava
 * @since 2020-11-02 19:03:30
 */
public interface NxtDeliveryCompanyService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtDeliveryCompany queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtDeliveryCompany> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nxtDeliveryCompany 实例对象
     * @return 实例对象
     */
    NxtDeliveryCompany insert(NxtDeliveryCompany nxtDeliveryCompany);

    /**
     * 修改数据
     *
     * @param nxtDeliveryCompany 实例对象
     * @return 实例对象
     */
    NxtDeliveryCompany update(NxtDeliveryCompany nxtDeliveryCompany);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}