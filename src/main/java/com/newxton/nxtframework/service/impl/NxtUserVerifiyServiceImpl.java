package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.entity.NxtUserVerifiy;
import com.newxton.nxtframework.dao.NxtUserVerifiyDao;
import com.newxton.nxtframework.service.NxtUserVerifiyService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtUserVerifiy)表服务实现类
 *
 * @author makejava
 * @since 2020-11-28 11:29:50
 */
@Service("nxtUserVerifiyService")
public class NxtUserVerifiyServiceImpl implements NxtUserVerifiyService {
    @Resource
    private NxtUserVerifiyDao nxtUserVerifiyDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtUserVerifiy queryById(Long id) {
        return this.nxtUserVerifiyDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtUserVerifiy> queryAllByLimit(int offset, int limit) {
        return this.nxtUserVerifiyDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtUserVerifiy 实例对象
     * @return 实例对象
     */
    @Override
    public NxtUserVerifiy insert(NxtUserVerifiy nxtUserVerifiy) {
        this.nxtUserVerifiyDao.insert(nxtUserVerifiy);
        return nxtUserVerifiy;
    }

    /**
     * 修改数据
     *
     * @param nxtUserVerifiy 实例对象
     * @return 实例对象
     */
    @Override
    public NxtUserVerifiy update(NxtUserVerifiy nxtUserVerifiy) {
        this.nxtUserVerifiyDao.update(nxtUserVerifiy);
        return this.queryById(nxtUserVerifiy.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtUserVerifiyDao.deleteById(id) > 0;
    }

    /**
     * 根据phoneOrEmail查询最近的那个验证码
     * @param phoneOrEmail
     * @return
     */
    public NxtUserVerifiy queryLastByPhoneOrEmail(@Param("phoneOrEmail") String phoneOrEmail){
        return this.nxtUserVerifiyDao.queryLastByPhoneOrEmail(phoneOrEmail);
    }

}