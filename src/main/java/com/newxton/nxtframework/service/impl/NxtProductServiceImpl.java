package com.newxton.nxtframework.service.impl;

import com.newxton.nxtframework.dao.NxtProductDao;
import com.newxton.nxtframework.entity.NxtProduct;
import com.newxton.nxtframework.service.NxtProductService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * (NxtProduct)表服务实现类
 *
 * @author makejava
 * @since 2020-08-03 10:21:56
 */
@Service("nxtProductService")
public class NxtProductServiceImpl implements NxtProductService {
    @Resource
    private NxtProductDao nxtProductDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtProduct queryById(Long id) {
        return this.nxtProductDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtProduct> queryAllByLimit(int offset, int limit) {
        return this.nxtProductDao.queryAllByLimit(offset, limit);
    }

    /**
     * 通过筛选条件查询指定行数据
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    public List<NxtProduct> selectAllByLimit(@Param("offset") int offset, @Param("limit") int limit,
                                             @Param("categoryId") Long categoryId){
        return this.nxtProductDao.selectAllByLimit(offset, limit, categoryId);
    }

    /**
     * 通过id类别批量查数据
     * @param offset
     * @param limit
     * @param idList
     * @return
     */
    public List<NxtProduct> selectByIdSet(@Param("offset") int offset, @Param("limit") int limit, @Param("idList") List<Long> idList){
        if (idList.size() == 0){
            return new ArrayList<>();
        }
        return this.nxtProductDao.selectByIdSet(offset, limit, idList);
    }

    /**
     * 查询指定多个类型数据
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    public List<NxtProduct> selectByCategoryIdSet(@Param("offset") int offset, @Param("limit") int limit,
                                                  @Param("categoryIdList") List<Long> categoryIdList){
        if (categoryIdList.size() == 0){
            return new ArrayList<>();
        }
        return this.nxtProductDao.selectByCategoryIdSet(offset, limit, categoryIdList);
    }

    /**
     * 通过搜索关键字查询数据
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    public List<NxtProduct> searchAllByLimit(@Param("offset") int offset, @Param("limit") int limit,
                                             @Param("keyword") String keyword){
        return this.nxtProductDao.searchAllByLimit(offset, limit, keyword);
    }

    /**
     * 通过分类id列表作为筛选条件查询Count
     * @return 对象列表
     */
    public Long countByCategoryIdSet(@Param("categoryIdList") List<Long> categoryIdList){
        if (categoryIdList.size() == 0){
            return 0L;
        }
        return this.nxtProductDao.countByCategoryIdSet(categoryIdList);
    }

    /**
     * 通过搜索关键字查询Count
     * @return 对象列表
     */
    public Long searchAllCount(@Param("keyword") String keyword){
        return this.nxtProductDao.searchAllCount(keyword);
    }

    /**
     * 通过实体作为筛选条件查询Count
     *
     * @param nxtProduct 实例对象
     * @return 对象列表
     */
    public Long queryCount(NxtProduct nxtProduct){
        return this.nxtProductDao.queryCount(nxtProduct);
    }

    /**
     * 通过实体作为筛选条件查询，且关键词搜索，且分页
     *
     * @param map 万能map
     * @return 对象列表
     */
    public List<NxtProduct> searchQueryAllByMap(Map<String,Object> map){
        return this.nxtProductDao.searchQueryAllByMap(map);
    }

    /**
     * 通过实体作为筛选条件查询，且关键词搜索 （统计总数）
     *
     * @param map 万能map
     * @return Long
     */
    public Long countSearchQueryAllByMap(Map<String,Object> map){
        return this.nxtProductDao.countSearchQueryAllByMap(map);
    }


    /**
     * 新增数据
     *
     * @param nxtProduct 实例对象
     * @return 实例对象
     */
    @Override
    public NxtProduct insert(NxtProduct nxtProduct) {
        this.nxtProductDao.insert(nxtProduct);
        return nxtProduct;
    }

    /**
     * 修改数据
     *
     * @param nxtProduct 实例对象
     * @return 实例对象
     */
    @Override
    public NxtProduct update(NxtProduct nxtProduct) {
        this.nxtProductDao.update(nxtProduct);
        return this.queryById(nxtProduct.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtProductDao.deleteById(id) > 0;
    }

    /**
     * 前台获取热卖产品
     * @param limit
     * @return
     */
    public List<NxtProduct> queryAllHot(int limit){
        return this.nxtProductDao.queryAllHot(limit);
    }

    /**
     * 前台获取热卖产品
     * @param limit
     * @return
     */
    public List<NxtProduct> queryAllNew(int limit){
        return this.nxtProductDao.queryAllNew(limit);
    }

    /**
     * 前台获取热卖产品
     * @param limit
     * @return
     */
    public List<NxtProduct> queryAllRecommend(int limit){
        return this.nxtProductDao.queryAllRecommend(limit);
    }

    /**
     * 后台admin获取产品列表
     * @param offset
     * @param limit
     * @param categoryId
     * @param searchKeyword
     * @param isRecommend
     * @param isNew
     * @param isHot
     * @param isSelling
     * @return
     */
    public List<NxtProduct> adminQueryAllByLimit(@Param("offset") Long offset, @Param("limit") Long limit, @Param("categoryId") Long categoryId, @Param("searchKeyword") String searchKeyword,@Param("isRecommend") Boolean isRecommend,@Param("isNew") Boolean isNew,@Param("isHot") Boolean isHot,@Param("isSelling") Boolean isSelling,@Param("isTrash") Boolean isTrash){
        return this.nxtProductDao.adminQueryAllByLimit(offset, limit, categoryId, searchKeyword, isRecommend, isNew, isHot, isSelling,isTrash);
    }

    /**
     * 后台admin统计产品总数
     * @param categoryId
     * @param searchKeyword
     * @param isRecommend
     * @param isNew
     * @param isHot
     * @param isSelling
     * @return
     */
    public Long adminCountAll(@Param("categoryId") Long categoryId, @Param("searchKeyword") String searchKeyword,@Param("isRecommend") Boolean isRecommend,@Param("isNew") Boolean isNew,@Param("isHot") Boolean isHot,@Param("isSelling") Boolean isSelling,@Param("isTrash") Boolean isTrash){
        return this.nxtProductDao.adminCountAll(categoryId, searchKeyword, isRecommend, isNew, isHot, isSelling,isTrash);
    }

}