package com.newxton.supercompanyapi.controller.api.admin;

import com.newxton.supercompanyapi.entity.NxtUploadfileCategory;
import com.newxton.supercompanyapi.service.NxtUploadfileCategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/23
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiAdminUploadfileCategoryUpdateController {

    @Resource
    private NxtUploadfileCategoryService nxtUploadfileCategoryService;

    @RequestMapping(value = "/api/admin/uploadfile_category/update", method = RequestMethod.POST)
    public Map<String, Object> index(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "category_name", required = false) String categoryName,
            @RequestParam(value = "category_pid", required = false) Long categoryPid

    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (id == null){
            result.put("status",52);
            result.put("message","参数错误");
            return result;
        }

        NxtUploadfileCategory category = nxtUploadfileCategoryService.queryById(id);

        if (category == null){
            result.put("status",48);
            result.put("message","该类别不存在");
            return result;
        }

        //设置新的名称
        if (categoryName != null){
            category.setCategoryName(categoryName.trim());
        }

        //设置新的上级类别
        if (categoryPid != null && !categoryPid.equals(category.getId())){

            //检查上级分类是否存在
            NxtUploadfileCategory parentCategory = nxtUploadfileCategoryService.queryById(categoryPid);
            if (parentCategory == null){
                result.put("status",48);
                result.put("message","该上级类别不存在");
                return result;
            }

            //检查categoryPid上级是否是自己的子孙分类
            List<NxtUploadfileCategory> list = nxtUploadfileCategoryService.queryAll(new NxtUploadfileCategory());
            boolean existSubCategoryOfCategoryPid = existSubCategoryOfCategoryPid(category,list,categoryPid);
            if (existSubCategoryOfCategoryPid){
                result.put("status",54);
                result.put("message","上级分类不可以是自己的子孙分类");
                return result;
            }
            else {
                category.setCategoryPid(categoryPid);
            }

        }

        NxtUploadfileCategory categoryUpdated = nxtUploadfileCategoryService.update(category);

        result.put("category",categoryUpdated);

        return result;

    }

    /**
     * 遍历搜寻下级类别，是否发现对应ID=CategoryPid的类别
     * @param preCategoryPid
     * @param list
     * @return
     */
    private boolean existSubCategoryOfCategoryPid(NxtUploadfileCategory categoryParent, List<NxtUploadfileCategory> list, Long preCategoryPid){

        boolean result = false;
        for (int i = 0; i < list.size(); i++) {
            NxtUploadfileCategory category = list.get(i);
            if (category.getCategoryPid().equals(categoryParent.getId())){
                if (category.getId().equals(preCategoryPid)){
                    return true;
                }
                else {
                    result = existSubCategoryOfCategoryPid(category,list,preCategoryPid);
                    if (result){
                        return result;
                    }
                }
            }
        }
        return result;

    }

}
