package com.newxton.companywebsite.service;

import com.newxton.companywebsite.entity.NxtGuestmessage;
import java.util.List;

/**
 * (NxtGuestmessage)表服务接口
 *
 * @author makejava
 * @since 2020-07-23 09:24:31
 */
public interface NxtGuestmessageService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtGuestmessage queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtGuestmessage> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param nxtGuestmessage 实例对象
     * @return 实例对象
     */
    NxtGuestmessage insert(NxtGuestmessage nxtGuestmessage);

    /**
     * 修改数据
     *
     * @param nxtGuestmessage 实例对象
     * @return 实例对象
     */
    NxtGuestmessage update(NxtGuestmessage nxtGuestmessage);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}