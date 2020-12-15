package com.newxton.nxtframework.controller.api.admin;

import com.newxton.nxtframework.component.NxtUploadImageComponent;
import com.newxton.nxtframework.entity.NxtProductBrand;
import com.newxton.nxtframework.entity.NxtUploadfile;
import com.newxton.nxtframework.struct.NxtStructProductBrand;
import com.newxton.nxtframework.service.NxtProductBrandService;
import com.newxton.nxtframework.service.NxtUploadfileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/4
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@RestController
public class NxtApiAdminProductBrandListController {

    @Resource
    private NxtProductBrandService nxtProductBrandService;

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @Resource
    private NxtUploadImageComponent nxtUploadImageComponent;

    @RequestMapping(value = "/api/admin/product_brand/list", method = RequestMethod.POST)
    public Map<String, Object> index() {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        List<NxtProductBrand> nxtProductBrands = nxtProductBrandService.queryAllOrderByNameASC();

        List<Long> uploadFileIdList = new ArrayList<>();

        for (NxtProductBrand item : nxtProductBrands) {
            uploadFileIdList.add(item.getUploadfileId());
        }

        List<NxtUploadfile> fileList = nxtUploadfileService.selectByIdSet(0,Integer.MAX_VALUE,uploadFileIdList);

        Map<Long,NxtUploadfile> mapFileIdToItem = new HashMap();

        for (NxtUploadfile item :
                fileList) {
            mapFileIdToItem.put(item.getId(),item);
        }

        List<NxtStructProductBrand> list = new ArrayList<>();

        for (NxtProductBrand item :
                nxtProductBrands) {

            NxtStructProductBrand nxtStructProductBrand = new NxtStructProductBrand();
            nxtStructProductBrand.setId(item.getId());
            nxtStructProductBrand.setBrandName(item.getBrandName());
            nxtStructProductBrand.setUploadFileId(item.getUploadfileId());

            NxtUploadfile nxtUploadfile = mapFileIdToItem.get(item.getUploadfileId());

            if (nxtUploadfile != null){
                nxtStructProductBrand.setPicUrlPath(nxtUploadfile.getUrlpath());
                nxtStructProductBrand.setPicUrlPathWithDomain(nxtUploadImageComponent.convertImagePathToDomainImagePath(nxtUploadfile.getUrlpath()));
            }

            list.add(nxtStructProductBrand);

            uploadFileIdList.add(item.getId());

        }

        result.put("list",list);

        return result;

    }

}
