package com.newxton.companywebsite.controller.api.admin;

import com.newxton.companywebsite.entity.NxtContent;
import com.newxton.companywebsite.entity.NxtUploadfile;
import com.newxton.companywebsite.entity.NxtUploadfileCategory;
import com.newxton.companywebsite.service.NxtContentService;
import com.newxton.companywebsite.service.NxtUploadfileCategoryService;
import com.newxton.companywebsite.service.NxtUploadfileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/24
 * @address Shenzhen, China
 * @github https://github.com/soyojoearth/newxton_company_website
 */
@RestController
public class NxtApiAdminUploadfileCategoryDeleteController {

    @Resource
    private NxtUploadfileCategoryService nxtUploadfileCategoryService;

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @RequestMapping(value = "/api/admin/uploadfile_category/delete", method = RequestMethod.POST)
    public Map<String, Object> index(@RequestParam(value = "id", required=false) Long id) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (id == null){
            result.put("status", 52);
            result.put("message", "参数错误");
            return result;
        }

        NxtUploadfileCategory category = nxtUploadfileCategoryService.queryById(id);

        if (category == null){
            result.put("status", 48);
            result.put("message", "该类别不存在");
            return result;
        }

        /*检查有没有子类*/
        NxtUploadfileCategory categoryCondition = new NxtUploadfileCategory();
        categoryCondition.setCategoryPid(category.getId());
        List<NxtUploadfileCategory> list = nxtUploadfileCategoryService.queryAll(categoryCondition);
        if (list.size()>0){
            result.put("status", 56);
            result.put("message", "类别下还有子类，请先删除子类");
            return result;
        }

        /*检查该类别下面有没有内容*/
        NxtUploadfile nxtUploadfile = new NxtUploadfile();
        nxtUploadfile.setCategoryId(category.getId());
        Long count = nxtUploadfileService.queryCount(nxtUploadfile);
        if (count > 0){
            result.put("status", 55);
            result.put("message", "类别下还有东西，请先转移掉");
            return result;
        }

        /*删除*/
        nxtUploadfileCategoryService.deleteById(category.getId());

        return result;

    }
}
