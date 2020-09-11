package com.newxton.companywebsite.service.impl;

import com.newxton.companywebsite.entity.NxtContent;
import com.newxton.companywebsite.dao.NxtContentDao;
import com.newxton.companywebsite.service.NxtContentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * (NxtContent)表服务实现类
 *
 * @author makejava
 * @since 2020-07-23 09:24:16
 */
@Service("nxtContentService")
public class NxtContentServiceImpl implements NxtContentService {
    @Resource
    private NxtContentDao nxtContentDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public NxtContent queryById(Long id) {
        return this.nxtContentDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<NxtContent> queryAllByLimit(int offset, int limit) {
        return this.nxtContentDao.queryAllByLimit(offset, limit);
    }

    /**
     * 通过筛选条件查询指定行数据
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    public List<NxtContent> selectAllByLimit(@Param("offset") int offset, @Param("limit") int limit,
                                             @Param("categoryId") Long categoryId){
        return this.nxtContentDao.selectAllByLimit(offset, limit, categoryId);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param nxtContent 实例对象
     * @return 对象列表
     */
    public List<NxtContent> queryAll(NxtContent nxtContent){
        return this.nxtContentDao.queryAll(nxtContent);
    }

    /**
     * 通过实体作为筛选条件查询Count
     *
     * @param nxtContent 实例对象
     * @return 对象列表
     */
    public Long queryCount(NxtContent nxtContent){
        return this.nxtContentDao.queryCount(nxtContent);
    }

    /**
     * 通过实体作为筛选条件查询，且关键词搜索，且分页
     *
     * @param map 万能map
     * @return 对象列表
     */
    public List<NxtContent> searchQueryAllByMap(Map<String,Object> map){
        return this.nxtContentDao.searchQueryAllByMap(map);
    }

    /**
     * 通过实体作为筛选条件查询，且关键词搜索 （统计总数）
     *
     * @param map 万能map
     * @return Long
     */
    public Long countSearchQueryAllByMap(Map<String,Object> map){
        return this.nxtContentDao.countSearchQueryAllByMap(map);
    }

    /**
     * 新增数据
     *
     * @param nxtContent 实例对象
     * @return 实例对象
     */
    @Override
    public NxtContent insert(NxtContent nxtContent) {
        this.nxtContentDao.insert(nxtContent);
        return nxtContent;
    }

    /**
     * 修改数据
     *
     * @param nxtContent 实例对象
     * @return 实例对象
     */
    @Override
    public NxtContent update(NxtContent nxtContent) {
        this.nxtContentDao.update(nxtContent);
        return this.queryById(nxtContent.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.nxtContentDao.deleteById(id) > 0;
    }
}