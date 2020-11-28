package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.entity.NxtUserVerify;
import com.newxton.nxtframework.dao.NxtUserVerifyDao;
import com.newxton.nxtframework.service.NxtUserVerifyService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtUserVerify)表服务实现类
 *
 * @author makejava
 * @since 2020-11-28 11:29:50
 */
@Service("nxtUserVerifyService")
public class NxtUserVerifyServiceImpl implements NxtUserVerifyService {
    @Resource
    private NxtUserVerifyDao nxtUserVerifyDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtUserVerify queryById(Long id) {
        return this.nxtUserVerifyDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtUserVerify> queryAllByLimit(int offset, int limit) {
        return this.nxtUserVerifyDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtUserVerify 实例对象
     * @return 实例对象
     */
    @Override
    public NxtUserVerify insert(NxtUserVerify nxtUserVerify) {
        this.nxtUserVerifyDao.insert(nxtUserVerify);
        return nxtUserVerify;
    }

    /**
     * 修改数据
     *
     * @param nxtUserVerify 实例对象
     * @return 实例对象
     */
    @Override
    public NxtUserVerify update(NxtUserVerify nxtUserVerify) {
        this.nxtUserVerifyDao.update(nxtUserVerify);
        return this.queryById(nxtUserVerify.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtUserVerifyDao.deleteById(id) > 0;
    }

    /**
     * 根据phoneOrEmail查询最近的那个验证码
     * @param phoneOrEmail
     * @return
     */
    public NxtUserVerify queryLastByPhoneOrEmail(@Param("phoneOrEmail") String phoneOrEmail){
        return this.nxtUserVerifyDao.queryLastByPhoneOrEmail(phoneOrEmail);
    }

}