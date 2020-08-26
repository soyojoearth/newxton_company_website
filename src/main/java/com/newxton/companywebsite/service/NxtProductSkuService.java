package com.newxton.companywebsite.service;

import com.newxton.companywebsite.entity.NxtProductSku;
import java.util.List;

/**
 * (NxtProductSku)表服务接口
 *
 * @author makejava
 * @since 2020-08-26 09:16:25
 */
public interface NxtProductSkuService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtProductSku queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtProductSku> queryAllByLimit(int offset, int limit);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtProductSku 实例对象
     * @return 对象列表
     */
    List<NxtProductSku> queryAll(NxtProductSku nxtProductSku);

    /**
     * 新增数据
     *
     * @param nxtProductSku 实例对象
     * @return 实例对象
     */
    NxtProductSku insert(NxtProductSku nxtProductSku);

    /**
     * 修改数据
     *
     * @param nxtProductSku 实例对象
     * @return 实例对象
     */
    NxtProductSku update(NxtProductSku nxtProductSku);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}