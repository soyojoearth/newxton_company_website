package com.newxton.supercompanyapi.service.impl;

import com.newxton.supercompanyapi.entity.NxtUploadfile;
import com.newxton.supercompanyapi.dao.NxtUploadfileDao;
import com.newxton.supercompanyapi.service.NxtUploadfileService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtUploadfile)表服务实现类
 *
 * @author makejava
 * @since 2020-07-23 09:25:37
 */
@Service("nxtUploadfileService")
public class NxtUploadfileServiceImpl implements NxtUploadfileService {
    @Resource
    private NxtUploadfileDao nxtUploadfileDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtUploadfile queryById(Long id) {
        return this.nxtUploadfileDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtUploadfile> queryAllByLimit(int offset, int limit) {
        return this.nxtUploadfileDao.queryAllByLimit(offset, limit);
    }

    /**
     * 查询指定多个类型数据
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    public List<NxtUploadfile> selectByIdSet(@Param("offset") int offset, @Param("limit") int limit,
                                                     @Param("idList") List<Long> idList){
        return this.nxtUploadfileDao.selectByIdSet(offset, limit, idList);
    }

    /**
     * 通过实体作为筛选条件查询Count
     *
     * @param nxtUploadfile 实例对象
     * @return 对象列表
     */
    public Long queryCount(NxtUploadfile nxtUploadfile){
        return this.nxtUploadfileDao.queryCount(nxtUploadfile);
    }

    /**
     * 新增数据
     *
     * @param nxtUploadfile 实例对象
     * @return 实例对象
     */
    @Override
    public NxtUploadfile insert(NxtUploadfile nxtUploadfile) {
        this.nxtUploadfileDao.insert(nxtUploadfile);
        return nxtUploadfile;
    }

    /**
     * 修改数据
     *
     * @param nxtUploadfile 实例对象
     * @return 实例对象
     */
    @Override
    public NxtUploadfile update(NxtUploadfile nxtUploadfile) {
        this.nxtUploadfileDao.update(nxtUploadfile);
        return this.queryById(nxtUploadfile.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtUploadfileDao.deleteById(id) > 0;
    }
}