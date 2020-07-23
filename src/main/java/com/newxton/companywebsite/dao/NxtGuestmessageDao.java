package com.newxton.companywebsite.dao;

import com.newxton.companywebsite.entity.NxtGuestmessage;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (NxtGuestmessage)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-23 09:24:31
 */
public interface NxtGuestmessageDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtGuestmessage queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtGuestmessage> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtGuestmessage 实例对象
     * @return 对象列表
     */
    List<NxtGuestmessage> queryAll(NxtGuestmessage nxtGuestmessage);

    /**
     * 新增数据
     *
     * @param nxtGuestmessage 实例对象
     * @return 影响行数
     */
    int insert(NxtGuestmessage nxtGuestmessage);

    /**
     * 修改数据
     *
     * @param nxtGuestmessage 实例对象
     * @return 影响行数
     */
    int update(NxtGuestmessage nxtGuestmessage);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}