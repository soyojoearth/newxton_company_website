package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.entity.NxtDeliveryRegion;
import com.newxton.nxtframework.dao.NxtDeliveryRegionDao;
import com.newxton.nxtframework.service.NxtDeliveryRegionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (NxtDeliveryRegion)表服务实现类
 *
 * @author makejava
 * @since 2020-11-02 19:03:31
 */
@Service("nxtDeliveryRegionService")
public class NxtDeliveryRegionServiceImpl implements NxtDeliveryRegionService {
    @Resource
    private NxtDeliveryRegionDao nxtDeliveryRegionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtDeliveryRegion queryById(Long id) {
        return this.nxtDeliveryRegionDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtDeliveryRegion> queryAllByLimit(int offset, int limit) {
        return this.nxtDeliveryRegionDao.queryAllByLimit(offset, limit);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtDeliveryRegion 实例对象
     * @return 对象列表
     */
    public List<NxtDeliveryRegion> queryAll(NxtDeliveryRegion nxtDeliveryRegion){
        return this.nxtDeliveryRegionDao.queryAll(nxtDeliveryRegion);
    }

    /**
     * 查询指定多个类型数据
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    public List<NxtDeliveryRegion> selectByIdSet(@Param("offset") int offset, @Param("limit") int limit,
                                          @Param("idList") List<Long> idList){
        if (idList.size() == 0){
            return new ArrayList<>();
        }
        return this.nxtDeliveryRegionDao.selectByIdSet(offset,limit,idList);
    }

    /**
     * 新增数据
     *
     * @param nxtDeliveryRegion 实例对象
     * @return 实例对象
     */
    @Override
    public NxtDeliveryRegion insert(NxtDeliveryRegion nxtDeliveryRegion) {
        this.nxtDeliveryRegionDao.insert(nxtDeliveryRegion);
        return nxtDeliveryRegion;
    }

    /**
     * 修改数据
     *
     * @param nxtDeliveryRegion 实例对象
     * @return 实例对象
     */
    @Override
    public NxtDeliveryRegion update(NxtDeliveryRegion nxtDeliveryRegion) {
        this.nxtDeliveryRegionDao.update(nxtDeliveryRegion);
        return this.queryById(nxtDeliveryRegion.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtDeliveryRegionDao.deleteById(id) > 0;
    }

    /**
     * 查询所有地区列表（为tree接口）
     * @return
     */
    @Override
    public List<NxtDeliveryRegion> queryAllRegionForTree(){
        return this.nxtDeliveryRegionDao.queryAllRegionForTree();
    }

}