package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.dao.NxtUserDao;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.service.NxtUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (NxtUser)表服务实现类
 *
 * @author makejava
 * @since 2020-07-23 09:25:55
 */
@Service("nxtUserService")
public class NxtUserServiceImpl implements NxtUserService {
    @Resource
    private NxtUserDao nxtUserDao;

    /**
     * 通过email查询
     *
     * @param email 用户名
     * @return 实例对象
     */
    public NxtUser queryByEmail(String email){
        return this.nxtUserDao.queryByEmail(email);
    }

    /**
     * 通过phone查询
     *
     * @param phone 用户名
     * @return 实例对象
     */
    public NxtUser queryByPhone(String phone){
        return this.nxtUserDao.queryByPhone(phone);
    }

    /**
     * 通过username查询单条数据
     *
     * @param username 用户名
     * @return 实例对象
     */
    public NxtUser queryByUsername(String username){
        return this.nxtUserDao.queryByUsername(username);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtUser queryById(Long id) {
        return this.nxtUserDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtUser> queryAllByLimit(int offset, int limit) {
        return this.nxtUserDao.queryAllByLimit(offset, limit);
    }

    /**
     * 查询所有管理员
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    public List<NxtUser> queryAllAdminUserByLimit(@Param("offset") int offset, @Param("limit") int limit){
        return this.nxtUserDao.queryAllAdminUserByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtUser 实例对象
     * @return 实例对象
     */
    @Override
    public NxtUser insert(NxtUser nxtUser) {
        this.nxtUserDao.insert(nxtUser);
        return nxtUser;
    }

    /**
     * 修改数据
     *
     * @param nxtUser 实例对象
     * @return 实例对象
     */
    @Override
    public NxtUser update(NxtUser nxtUser) {
        this.nxtUserDao.update(nxtUser);
        return this.queryById(nxtUser.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtUserDao.deleteById(id) > 0;
    }

    /**
     * 通过inviteCode查询单条数据
     *
     * @param inviteCode 推广码
     * @return 实例对象
     */
    @Override
    public NxtUser queryByInviteCode(String inviteCode) {
        return this.nxtUserDao.queryByInviteCode(inviteCode);
    }

    /**
     * 根据userId列表批量查询user
     * @param offset
     * @param limit
     * @param idList
     * @return
     */
    public List<NxtUser> selectByIdSet(@Param("offset") int offset, @Param("limit") int limit, @Param("idList") List<Long> idList){
        if (idList.size()==0){
            return new ArrayList<>();
        }
        return this.nxtUserDao.selectByIdSet(offset,limit,idList);
    }

    /**
     * 查询某用户的1级下家列表
     * @param offset
     * @param limit
     * @param inviterUserId
     * @return
     */
    public List<NxtUser> queryAllLevelOneInviteesUserIdLimit(@Param("offset") Long offset, @Param("limit") Long limit, @Param("inviterUserId") Long inviterUserId){
        return this.nxtUserDao.queryAllLevelOneInviteesUserIdLimit(offset,limit,inviterUserId);
    }

    /**
     * 查询某用户的2级下家列表
     * @param offset
     * @param limit
     * @param inviterUserId
     * @return
     */
    public List<NxtUser> queryAllLevelTwoInviteesUserIdLimit(@Param("offset") Long offset, @Param("limit") Long limit, @Param("inviterUserId") Long inviterUserId){
        return this.nxtUserDao.queryAllLevelTwoInviteesUserIdLimit(offset,limit,inviterUserId);
    }

    /**
     * 查询某用户的3级下家列表
     * @param offset
     * @param limit
     * @param inviterUserId
     * @return
     */
    public List<NxtUser> queryAllLevelThreeInviteesUserIdLimit(@Param("offset") Long offset, @Param("limit") Long limit, @Param("inviterUserId") Long inviterUserId){
        return this.nxtUserDao.queryAllLevelThreeInviteesUserIdLimit(offset,limit,inviterUserId);
    }

    /**
     * 解绑用户手机号
     * @param id
     * @return NxtUser
     */
    public NxtUser removePhoneById(@Param("id") Long id){
        this.nxtUserDao.removePhoneById(id);
        return this.queryById(id);
    }

    /**
     * 解绑用户Email
     * @param id
     * @return NxtUser
     */
    public NxtUser removeEmailById(@Param("id") Long id){
        this.nxtUserDao.removeEmailById(id);
        return this.queryById(id);
    }

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
    @Override
    public List<NxtUser> adminQueryMemberList(Long offset, Long limit, Long userId, String username, Integer levelNum, Long datelineCreateBegin, Long datelineCreateEnd){
        return this.nxtUserDao.adminQueryMemberList(offset, limit, userId, username, levelNum, datelineCreateBegin, datelineCreateEnd);
    }

    /**
     * 获取会员统计数字
     * @param userId
     * @param username
     * @param levelNum
     * @param datelineCreateBegin
     * @param datelineCreateEnd
     * @return
     */
    @Override
    public Long adminQueryMemberCount(Long userId, String username, Integer levelNum, Long datelineCreateBegin, Long datelineCreateEnd){
        return this.nxtUserDao.adminQueryMemberCount(userId, username, levelNum, datelineCreateBegin, datelineCreateEnd);
    }


}