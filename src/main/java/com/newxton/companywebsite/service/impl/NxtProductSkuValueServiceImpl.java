package com.newxton.companywebsite.service.impl;

import com.newxton.companywebsite.entity.NxtProductSkuValue;
import com.newxton.companywebsite.dao.NxtProductSkuValueDao;
import com.newxton.companywebsite.service.NxtProductSkuValueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtProductSkuValue)表服务实现类
 *
 * @author makejava
 * @since 2020-08-26 09:16:41
 */
@Service("nxtProductSkuValueService")
public class NxtProductSkuValueServiceImpl implements NxtProductSkuValueService {
    @Resource
    private NxtProductSkuValueDao nxtProductSkuValueDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtProductSkuValue queryById(Long id) {
        return this.nxtProductSkuValueDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtProductSkuValue> queryAllByLimit(int offset, int limit) {
        return this.nxtProductSkuValueDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtProductSkuValue 实例对象
     * @return 实例对象
     */
    @Override
    public NxtProductSkuValue insert(NxtProductSkuValue nxtProductSkuValue) {
        this.nxtProductSkuValueDao.insert(nxtProductSkuValue);
        return nxtProductSkuValue;
    }

    /**
     * 修改数据
     *
     * @param nxtProductSkuValue 实例对象
     * @return 实例对象
     */
    @Override
    public NxtProductSkuValue update(NxtProductSkuValue nxtProductSkuValue) {
        this.nxtProductSkuValueDao.update(nxtProductSkuValue);
        return this.queryById(nxtProductSkuValue.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtProductSkuValueDao.deleteById(id) > 0;
    }
}