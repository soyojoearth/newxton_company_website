package com.newxton.companywebsite.service.impl;

import com.newxton.companywebsite.entity.NxtUser;
import com.newxton.companywebsite.dao.NxtUserDao;
import com.newxton.companywebsite.service.NxtUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}