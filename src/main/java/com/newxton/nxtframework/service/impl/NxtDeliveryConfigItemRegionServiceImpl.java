package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.entity.NxtDeliveryConfigItemRegion;
import com.newxton.nxtframework.dao.NxtDeliveryConfigItemRegionDao;
import com.newxton.nxtframework.service.NxtDeliveryConfigItemRegionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (NxtDeliveryConfigItemRegion)表服务实现类
 *
 * @author makejava
 * @since 2020-11-02 19:03:31
 */
@Service("nxtDeliveryConfigItemRegionService")
public class NxtDeliveryConfigItemRegionServiceImpl implements NxtDeliveryConfigItemRegionService {
    @Resource
    private NxtDeliveryConfigItemRegionDao nxtDeliveryConfigItemRegionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtDeliveryConfigItemRegion queryById(Long id) {
        return this.nxtDeliveryConfigItemRegionDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtDeliveryConfigItemRegion> queryAllByLimit(int offset, int limit) {
        return this.nxtDeliveryConfigItemRegionDao.queryAllByLimit(offset, limit);
    }

    /**
     * 根据idList查询多条记录
     *
     * @return 对象列表
     */
    public List<NxtDeliveryConfigItemRegion> selectByConfigItemIdSet(@Param("idList") List<Long> idList){
        if (idList.size() == 0){
            return new ArrayList<>();
        }
        return this.nxtDeliveryConfigItemRegionDao.selectByConfigItemIdSet(idList);
    }

    /**
     * 通过实体作为筛选条件查询Count
     *
     * @param nxtDeliveryConfigItemRegion 实例对象
     * @return 对象列表
     */
    public Long queryCount(NxtDeliveryConfigItemRegion nxtDeliveryConfigItemRegion){
        return this.nxtDeliveryConfigItemRegionDao.queryCount(nxtDeliveryConfigItemRegion);
    }

    /**
     * 新增数据
     *
     * @param nxtDeliveryConfigItemRegion 实例对象
     * @return 实例对象
     */
    @Override
    public NxtDeliveryConfigItemRegion insert(NxtDeliveryConfigItemRegion nxtDeliveryConfigItemRegion) {
        this.nxtDeliveryConfigItemRegionDao.insert(nxtDeliveryConfigItemRegion);
        return nxtDeliveryConfigItemRegion;
    }

    /**
     * 修改数据
     *
     * @param nxtDeliveryConfigItemRegion 实例对象
     * @return 实例对象
     */
    @Override
    public NxtDeliveryConfigItemRegion update(NxtDeliveryConfigItemRegion nxtDeliveryConfigItemRegion) {
        this.nxtDeliveryConfigItemRegionDao.update(nxtDeliveryConfigItemRegion);
        return this.queryById(nxtDeliveryConfigItemRegion.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtDeliveryConfigItemRegionDao.deleteById(id) > 0;
    }

    /**
     * 删除指定多个类型数据
     * @return 对象列表
     */
    public void deleteByConfigItemIdSet(@Param("idList") List<Long> idList){
        this.nxtDeliveryConfigItemRegionDao.deleteByConfigItemIdSet(idList);
    }

    /**
     * 删除指定多个类型数据
     * @return 对象列表
     */
    public int deleteByIdSet(@Param("idList") List<Long> idList){
        return this.nxtDeliveryConfigItemRegionDao.deleteByIdSet(idList);
    }

}