package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.entity.NxtDeliveryCompany;
import com.newxton.nxtframework.dao.NxtDeliveryCompanyDao;
import com.newxton.nxtframework.service.NxtDeliveryCompanyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (NxtDeliveryCompany)表服务实现类
 *
 * @author makejava
 * @since 2020-11-02 19:03:30
 */
@Service("nxtDeliveryCompanyService")
public class NxtDeliveryCompanyServiceImpl implements NxtDeliveryCompanyService {
    @Resource
    private NxtDeliveryCompanyDao nxtDeliveryCompanyDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtDeliveryCompany queryById(Long id) {
        return this.nxtDeliveryCompanyDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtDeliveryCompany> queryAllByLimit(int offset, int limit) {
        return this.nxtDeliveryCompanyDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param nxtDeliveryCompany 实例对象
     * @return 实例对象
     */
    @Override
    public NxtDeliveryCompany insert(NxtDeliveryCompany nxtDeliveryCompany) {
        this.nxtDeliveryCompanyDao.insert(nxtDeliveryCompany);
        return nxtDeliveryCompany;
    }

    /**
     * 修改数据
     *
     * @param nxtDeliveryCompany 实例对象
     * @return 实例对象
     */
    @Override
    public NxtDeliveryCompany update(NxtDeliveryCompany nxtDeliveryCompany) {
        this.nxtDeliveryCompanyDao.update(nxtDeliveryCompany);
        return this.queryById(nxtDeliveryCompany.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtDeliveryCompanyDao.deleteById(id) > 0;
    }
}