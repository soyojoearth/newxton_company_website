package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.entity.NxtProductSkuValuePriceEtc;
import com.newxton.nxtframework.dao.NxtProductSkuValuePriceEtcDao;
import com.newxton.nxtframework.service.NxtProductSkuValuePriceEtcService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (NxtProductSkuValuePriceEtc)表服务实现类
 *
 * @author makejava
 * @since 2020-11-03 16:57:15
 */
@Service("nxtProductSkuValuePriceEtcService")
public class NxtProductSkuValuePriceEtcServiceImpl implements NxtProductSkuValuePriceEtcService {
    @Resource
    private NxtProductSkuValuePriceEtcDao nxtProductSkuValuePriceEtcDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtProductSkuValuePriceEtc queryById(Long id) {
        return this.nxtProductSkuValuePriceEtcDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtProductSkuValuePriceEtc> queryAllByLimit(int offset, int limit) {
        return this.nxtProductSkuValuePriceEtcDao.queryAllByLimit(offset, limit);
    }

    /**
     * 查询指定多个类型数据
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    public List<NxtProductSkuValuePriceEtc> selectByValueIdSet(@Param("offset") int offset, @Param("limit") int limit,
                                                   @Param("valueIdList") List<Long> valueIdList){
        if (valueIdList.size() == 0){
            return new ArrayList<>();
        }
        return this.nxtProductSkuValuePriceEtcDao.selectByValueIdSet(offset, limit, valueIdList);
    }

    /**
     * 删除指定多个类型数据
     * @return 对象列表
     */
    public List<NxtProductSkuValuePriceEtc> deleteByValueIdSet(@Param("valueIdList") List<Long> valueIdList){
        return this.nxtProductSkuValuePriceEtcDao.deleteByValueIdSet(valueIdList);

    }

    /**
     * 新增数据
     *
     * @param nxtProductSkuValuePriceEtc 实例对象
     * @return 实例对象
     */
    @Override
    public NxtProductSkuValuePriceEtc insert(NxtProductSkuValuePriceEtc nxtProductSkuValuePriceEtc) {
        this.nxtProductSkuValuePriceEtcDao.insert(nxtProductSkuValuePriceEtc);
        return nxtProductSkuValuePriceEtc;
    }

    /**
     * 修改数据
     *
     * @param nxtProductSkuValuePriceEtc 实例对象
     * @return 实例对象
     */
    @Override
    public NxtProductSkuValuePriceEtc update(NxtProductSkuValuePriceEtc nxtProductSkuValuePriceEtc) {
        this.nxtProductSkuValuePriceEtcDao.update(nxtProductSkuValuePriceEtc);
        return this.queryById(nxtProductSkuValuePriceEtc.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtProductSkuValuePriceEtcDao.deleteById(id) > 0;
    }
}