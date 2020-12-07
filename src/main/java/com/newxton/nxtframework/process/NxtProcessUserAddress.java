package com.newxton.nxtframework.process;

import com.newxton.nxtframework.entity.NxtDeliveryRegion;
import com.newxton.nxtframework.entity.NxtUserAddress;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.service.NxtDeliveryRegionService;
import com.newxton.nxtframework.service.NxtUserAddressService;
import com.newxton.nxtframework.struct.NxtStructUserAddress;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/1
 * @address Shenzhen, China
 */
@Component
public class NxtProcessUserAddress {

    @Resource
    private NxtUserAddressService nxtUserAddressService;

    @Resource
    private NxtDeliveryRegionService nxtDeliveryRegionService;

    /**
     * 取得用户收货地址
     * @param offset
     * @param limit
     * @param userId
     * @return
     */
    public List<NxtStructUserAddress> userAddressList(Long offset,Long limit, Long userId){

        List<NxtStructUserAddress> nxtStructUserAddressList = new ArrayList<>();

        List<NxtUserAddress> list = nxtUserAddressService.queryAllByUserIdLimit(offset,limit,userId);

        Set<Long> regionIdSet = new HashSet<>();

        for (NxtUserAddress item :
                list) {
            regionIdSet.add(item.getRegionCountry());
            regionIdSet.add(item.getRegionProvince());
            regionIdSet.add(item.getRegionCity());
            NxtStructUserAddress nxtStructUserAddress = new NxtStructUserAddress();
            nxtStructUserAddress.setId(item.getId());
            nxtStructUserAddress.setRegionCountry(item.getRegionCountry());
            nxtStructUserAddress.setRegionCity(item.getRegionCity());
            nxtStructUserAddress.setRegionProvince(item.getRegionProvince());
            nxtStructUserAddress.setDeliveryAddress(item.getDeliveryAddress());
            nxtStructUserAddress.setDeliveryPerson(item.getDeliveryPerson());
            nxtStructUserAddress.setDeliveryPhone(item.getDeliveryPhone());
            nxtStructUserAddress.setDeliveryPostcode(item.getDeliveryPostcode());
            nxtStructUserAddress.setIsDefault(item.getIsDefault() > 0);
            nxtStructUserAddressList.add(nxtStructUserAddress);
        }

        //取region名称
        Map<Long,String> mapRegionIdToName = new HashMap<>();
        List<Long> regionIdList = new ArrayList<>();
        regionIdList.addAll(regionIdSet);
        List<NxtDeliveryRegion> nxtDeliveryRegionList = nxtDeliveryRegionService.selectByIdSet(0,Integer.MAX_VALUE,regionIdList);

        for (NxtDeliveryRegion item : nxtDeliveryRegionList) {
            mapRegionIdToName.put(item.getId(),item.getRegionName());
        }

        //国家、省份、城市名称
        for (NxtStructUserAddress nxtStructUserAddress :
                nxtStructUserAddressList) {
            nxtStructUserAddress.setRegionCountryName(mapRegionIdToName.getOrDefault(nxtStructUserAddress.getRegionCountry(),"--"));
            nxtStructUserAddress.setRegionProvinceName(mapRegionIdToName.getOrDefault(nxtStructUserAddress.getRegionProvince(),"--"));
            nxtStructUserAddress.setRegionCityName(mapRegionIdToName.getOrDefault(nxtStructUserAddress.getRegionCity(),"--"));
        }

        return nxtStructUserAddressList;
    }

    /**
     * 用户地址保存/编辑/新增(有id表示修改，无id表示新增)
     * @param userId
     * @param nxtStructUserAddress
     */
    @Transactional(rollbackFor=Exception.class)
    public void saveUserAddress(Long userId, NxtStructUserAddress nxtStructUserAddress) throws NxtException {

        NxtUserAddress nxtUserAddress;
        if (nxtStructUserAddress.getId() != null){
            nxtUserAddress = nxtUserAddressService.queryById(nxtStructUserAddress.getId());
            if (nxtUserAddress == null){
                throw new NxtException("该收货地址记录不存在");
            }
            if (!nxtUserAddress.getUserId().equals(userId)){
                throw new NxtException("该地址不属于当前用户");
            }
        }
        else {
            nxtUserAddress = new NxtUserAddress();
        }

        if (nxtStructUserAddress.getDeliveryAddress() == null || nxtStructUserAddress.getDeliveryAddress().isEmpty()){
            throw new NxtException("请提供详细地址");
        }
        if (nxtStructUserAddress.getDeliveryPerson() == null || nxtStructUserAddress.getDeliveryPerson().isEmpty()){
            throw new NxtException("请提提供收件人");
        }
        if (nxtStructUserAddress.getDeliveryPhone() == null || nxtStructUserAddress.getDeliveryPhone().isEmpty()){
            throw new NxtException("请提提供联系电话");
        }

        //验证国家
        NxtDeliveryRegion nxtDeliveryRegionCountry = nxtDeliveryRegionService.queryById(nxtStructUserAddress.getRegionCountry());
        if (nxtDeliveryRegionCountry == null){
            throw new NxtException("国家选择不对");
        }
        //验证省份
        NxtDeliveryRegion nxtDeliveryRegionProvince = nxtDeliveryRegionService.queryById(nxtStructUserAddress.getRegionProvince());
        if (nxtDeliveryRegionProvince == null){
            throw new NxtException("省份选择不对");
        }
        else if (!nxtDeliveryRegionProvince.getRegionPid().equals(nxtDeliveryRegionCountry.getId())){
            throw new NxtException("省份对不上国家");
        }

        //验证城市
        NxtDeliveryRegion nxtDeliveryRegionCity = nxtDeliveryRegionService.queryById(nxtStructUserAddress.getRegionCity());
        if (nxtDeliveryRegionCity == null){
            throw new NxtException("城市选择不对");
        }
        else if (!nxtDeliveryRegionCity.getRegionPid().equals(nxtDeliveryRegionProvince.getId())){
            throw new NxtException("城市对不上省份");
        }

        nxtUserAddress.setUserId(userId);
        nxtUserAddress.setRegionCountry(nxtStructUserAddress.getRegionCountry());
        nxtUserAddress.setRegionProvince(nxtStructUserAddress.getRegionProvince());
        nxtUserAddress.setRegionCity(nxtStructUserAddress.getRegionCity());
        nxtUserAddress.setDeliveryAddress(nxtStructUserAddress.getDeliveryAddress());
        nxtUserAddress.setDeliveryPerson(nxtStructUserAddress.getDeliveryPerson());
        nxtUserAddress.setDeliveryPhone(nxtStructUserAddress.getDeliveryPhone());
        nxtUserAddress.setDeliveryPostcode(nxtStructUserAddress.getDeliveryPostcode());
        nxtUserAddress.setIsDefault(nxtStructUserAddress.getIsDefault() ? 1 : 0);

        if (nxtUserAddress.getId() == null){
            nxtUserAddressService.insert(nxtUserAddress);
        }
        else {
            nxtUserAddressService.update(nxtUserAddress);
        }

        List<NxtUserAddress> nxtUserAddressList = nxtUserAddressService.queryAllByUserIdLimit(0L,Long.MAX_VALUE,userId);

        if (!nxtStructUserAddress.getIsDefault()){
            //检查是否要强制默认
            if ( nxtUserAddressList.size() == 1){
                nxtUserAddress.setIsDefault(1);
                nxtUserAddressService.update(nxtUserAddress);
            }
        }
        else {
            //占领默认值
            if (nxtUserAddressList.size() > 1){
                for (NxtUserAddress item : nxtUserAddressList) {
                    if (!item.getId().equals(nxtUserAddress.getId()) && item.getIsDefault() != null && item.getIsDefault().equals(1)){
                        item.setIsDefault(0);
                        nxtUserAddressService.update(item);
                    }
                }
            }
        }

    }

    /**
     * 将用户某收货地址设为默认值
     * @param userId
     * @param id
     */
    @Transactional(rollbackFor=Exception.class)
    public void setDefault(Long userId,Long id) throws NxtException {

        NxtUserAddress nxtUserAddress = nxtUserAddressService.queryById(id);

        if (nxtUserAddress == null){
            throw new NxtException("该收货地址记录不存在");
        }
        if (!nxtUserAddress.getUserId().equals(userId)){
            throw new NxtException("该地址不属于当前用户");
        }

        nxtUserAddress.setIsDefault(1);
        nxtUserAddressService.update(nxtUserAddress);

        List<NxtUserAddress> nxtUserAddressList = nxtUserAddressService.queryAllByUserIdLimit(0L,Long.MAX_VALUE,userId);

        //占领默认值
        if (nxtUserAddressList.size() > 1){
            for (NxtUserAddress item : nxtUserAddressList) {
                if (!item.getId().equals(nxtUserAddress.getId()) && item.getIsDefault() != null && item.getIsDefault().equals(1)){
                    item.setIsDefault(0);
                    nxtUserAddressService.update(item);
                }
            }
        }

    }

}
