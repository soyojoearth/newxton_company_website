package com.newxton.companywebsite.service;

import com.newxton.companywebsite.entity.NxtSetting;
import java.util.List;

/**
 * (NxtSetting)表服务接口
 *
 * @author makejava
 * @since 2020-07-23 09:25:30
 */
public interface NxtSettingService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtSetting queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtSetting> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nxtSetting 实例对象
     * @return 实例对象
     */
    NxtSetting insert(NxtSetting nxtSetting);

    /**
     * 修改数据
     *
     * @param nxtSetting 实例对象
     * @return 实例对象
     */
    NxtSetting update(NxtSetting nxtSetting);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}