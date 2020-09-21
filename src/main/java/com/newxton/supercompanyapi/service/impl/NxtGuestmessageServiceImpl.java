package com.newxton.supercompanyapi.service.impl;

import com.newxton.supercompanyapi.entity.NxtGuestmessage;
import com.newxton.supercompanyapi.dao.NxtGuestmessageDao;
import com.newxton.supercompanyapi.service.NxtGuestmessageService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtGuestmessage)表服务实现类
 *
 * @author makejava
 * @since 2020-07-23 09:24:31
 */
@Service("nxtGuestmessageService")
public class NxtGuestmessageServiceImpl implements NxtGuestmessageService {
    @Resource
    private NxtGuestmessageDao nxtGuestmessageDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtGuestmessage queryById(Long id) {
        return this.nxtGuestmessageDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtGuestmessage> queryAllByLimit(int offset, int limit) {
        return this.nxtGuestmessageDao.queryAllByLimit(offset, limit);
    }

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    public List<NxtGuestmessage> queryDescAllByLimit(@Param("offset") int offset, @Param("limit") int limit){
        return this.nxtGuestmessageDao.queryDescAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtGuestmessage 实例对象
     * @return 实例对象
     */
    @Override
    public NxtGuestmessage insert(NxtGuestmessage nxtGuestmessage) {
        this.nxtGuestmessageDao.insert(nxtGuestmessage);
        return nxtGuestmessage;
    }

    /**
     * 修改数据
     *
     * @param nxtGuestmessage 实例对象
     * @return 实例对象
     */
    @Override
    public NxtGuestmessage update(NxtGuestmessage nxtGuestmessage) {
        this.nxtGuestmessageDao.update(nxtGuestmessage);
        return this.queryById(nxtGuestmessage.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtGuestmessageDao.deleteById(id) > 0;
    }
}