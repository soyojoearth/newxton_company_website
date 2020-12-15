package com.newxton.nxtframework.service;

import com.newxton.nxtframework.entity.NxtUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (NxtUser)表服务接口
 *
 * @author makejava
 * @since 2020-07-23 09:25:55
 */
public interface NxtUserService {

    /**
     * 通过email查询
     *
     * @param email 用户名
     * @return 实例对象
     */
    NxtUser queryByEmail(String email);

    /**
     * 通过phone查询
     *
     * @param phone 用户名
     * @return 实例对象
     */
    NxtUser queryByPhone(String phone);

    /**
     * 通过username查询
     *
     * @param username 用户名
     * @return 实例对象
     */
    NxtUser queryByUsername(String username);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    NxtUser queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtUser> queryAllByLimit(int offset, int limit);

    /**
     * 查询所有管理员
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<NxtUser> queryAllAdminUserByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 新增数据
     *
     * @param nxtUser 实例对象
     * @return 实例对象
     */
    NxtUser insert(NxtUser nxtUser);

    /**
     * 修改数据
     *
     * @param nxtUser 实例对象
     * @return 实例对象
     */
    NxtUser update(NxtUser nxtUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 通过inviteCode查询
     *
     * @param inviteCode 推广码
     * @return 实例对象
     */
    NxtUser queryByInviteCode(String inviteCode);

    /**
     * 根据userId列表批量查询user
     * @param offset
     * @param limit
     * @param idList
     * @return
     */
    List<NxtUser> selectByIdSet(@Param("offset") int offset, @Param("limit") int limit, @Param("idList") List<Long> idList);

    /**
     * 查询某用户的1级下家列表
     * @param offset
     * @param limit
     * @param inviterUserId
     * @return
     */
    List<NxtUser> queryAllLevelOneInviteesUserIdLimit(@Param("offset") Long offset, @Param("limit") Long limit, @Param("inviterUserId") Long inviterUserId);

    /**
     * 查询某用户的2级下家列表
     * @param offset
     * @param limit
     * @param inviterUserId
     * @return
     */
    List<NxtUser> queryAllLevelTwoInviteesUserIdLimit(@Param("offset") Long offset, @Param("limit") Long limit, @Param("inviterUserId") Long inviterUserId);

    /**
     * 查询某用户的3级下家列表
     * @param offset
     * @param limit
     * @param inviterUserId
     * @return
     */
    List<NxtUser> queryAllLevelThreeInviteesUserIdLimit(@Param("offset") Long offset, @Param("limit") Long limit, @Param("inviterUserId") Long inviterUserId);

    /**
     * 解绑用户手机号
     * @param id
     * @return NxtUser
     */
    NxtUser removePhoneById(@Param("id") Long id);

    /**
     * 解绑用户Email
     * @param id
     * @return NxtUser
     */
    NxtUser removeEmailById(@Param("id") Long id);

    /**
     * 获取会员列表
     * @param offset
     * @param limit
     * @param userId
     * @param username
     * @param levelNum
     * @param datelineCreateBegin
     * @param datelineCreateEnd
     * @return
     */
    List<NxtUser> adminQueryMemberList(Long offset, Long limit, Long userId, String username, Integer levelNum, Long datelineCreateBegin, Long datelineCreateEnd);

    /**
     * 获取会员统计数字
     * @param userId
     * @param username
     * @param levelNum
     * @param datelineCreateBegin
     * @param datelineCreateEnd
     * @return
     */
    Long adminQueryMemberCount(Long userId, String username, Integer levelNum, Long datelineCreateBegin, Long datelineCreateEnd);

}