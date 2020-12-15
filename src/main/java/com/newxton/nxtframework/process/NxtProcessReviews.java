package com.newxton.nxtframework.process;

import com.newxton.nxtframework.component.NxtUploadImageComponent;
import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.service.*;
import com.newxton.nxtframework.struct.*;
import com.newxton.nxtframework.struct.admin.NxtStructAdminProductReviewsItem;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/23
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Component
public class NxtProcessReviews {

    @Resource
    private NxtUserService nxtUserService;

    @Resource
    private NxtReviewsService nxtReviewsService;

    @Resource
    private NxtReviewsPictureService nxtReviewsPictureService;

    @Resource
    private NxtUploadfileService nxtUploadfileService;

    @Resource
    private NxtUploadImageComponent nxtUploadImageComponent;

    @Resource
    private NxtOrderFormService nxtOrderFormService;

    @Resource
    private NxtProductService nxtProductService;

    @Resource
    private NxtOrderFormProductService nxtOrderFormProductService;

    /**
     * 管理员后台，获取评论数量
     * @param offset
     * @param limit
     * @param productId
     * @return
     */
    public Long adminProductReviewsItemCount(Long offset, Long limit, Long productId){
        Long count = nxtReviewsService.adminQueryReviewsCount(offset,limit,productId);
        return count;
    }

    /**
     * 管理员后台，获取评论列表，包含该评论的对应产品信息
     * @param offset
     * @param limit
     * @param productId
     * @return
     */
    public List<NxtStructAdminProductReviewsItem> adminProductReviewsItemList(Long offset, Long limit, Long productId){
        List<NxtStructAdminProductReviewsItem> list = new ArrayList<>();

        List<NxtReviews> nxtReviewsList = nxtReviewsService.adminQueryReviewsList(offset,limit,productId);

        List<Long> orderFormProductIdList = new ArrayList<>();
        List<Long> userIdList = new ArrayList<>();

        for (NxtReviews nxtReviews : nxtReviewsList) {
            orderFormProductIdList.add(nxtReviews.getOrderFormProductId());
            userIdList.add(nxtReviews.getUserId());
        }

        //查询用户
        List<NxtUser> nxtUserList = nxtUserService.selectByIdSet(0,Integer.MAX_VALUE,userIdList);
        Map<Long,String> mapUserIdToName = new HashMap<>();
        for (NxtUser nxtUser : nxtUserList) {
            mapUserIdToName.put(nxtUser.getId(),nxtUser.getUsername());
        }


        //查询主图&产品名称
        List<NxtOrderFormProduct> nxtOrderFormProductList = nxtOrderFormProductService.selectAllByIdSet(orderFormProductIdList);

        Map<Long,String> mapOrderFormProductIdToProductName = new HashMap<>();
        List<Long> uploadFileIdList = new ArrayList<>();
        for (NxtOrderFormProduct nxtOrderFormProduct : nxtOrderFormProductList) {
            uploadFileIdList.add(nxtOrderFormProduct.getProductPicUploadfileId());
            mapOrderFormProductIdToProductName.put(nxtOrderFormProduct.getId(),nxtOrderFormProduct.getProductName());
        }

        Map<Long,String> mapUploadFileIdToPicUrl = new HashMap<>();
        List<NxtUploadfile> nxtUploadfileList = nxtUploadfileService.selectByIdSet(0,Integer.MAX_VALUE,uploadFileIdList);

        for (NxtUploadfile nxtUploadfile : nxtUploadfileList) {
            mapUploadFileIdToPicUrl.put(nxtUploadfile.getId(),nxtUploadImageComponent.convertImagePathToDomainImagePath(nxtUploadfile.getUrlpath()));
        }

        Map<Long,String> mapOrderFormProductIdToPicUrl = new HashMap<>();
        for (NxtOrderFormProduct nxtOrderFormProduct : nxtOrderFormProductList) {
            String urlPath = mapUploadFileIdToPicUrl.getOrDefault(nxtOrderFormProduct.getProductPicUploadfileId(),"/common/images/empty.png");
            mapOrderFormProductIdToPicUrl.put(nxtOrderFormProduct.getId(),urlPath);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (NxtReviews nxtReviews : nxtReviewsList) {
            NxtStructAdminProductReviewsItem nxtStructAdminProductReviewsItem = new NxtStructAdminProductReviewsItem();
            nxtStructAdminProductReviewsItem.setUserId(nxtReviews.getUserId());
            nxtStructAdminProductReviewsItem.setContent(nxtReviews.getContent());
            nxtStructAdminProductReviewsItem.setDate(sdf.format(new Date(nxtReviews.getDateline())));
            nxtStructAdminProductReviewsItem.setId(nxtReviews.getId());
            nxtStructAdminProductReviewsItem.setOrderFormId(nxtReviews.getOrderFormId());
            String productName = mapOrderFormProductIdToProductName.get(nxtReviews.getOrderFormProductId());
            nxtStructAdminProductReviewsItem.setProductName(productName);
            nxtStructAdminProductReviewsItem.setProductPicUrl(mapOrderFormProductIdToPicUrl.get(nxtReviews.getOrderFormProductId()));
            nxtStructAdminProductReviewsItem.setUsername(mapUserIdToName.get(nxtReviews.getUserId()));
            nxtStructAdminProductReviewsItem.setRecommend(nxtReviews.getIsRecommend() > 0);
            nxtStructAdminProductReviewsItem.setHidden(nxtReviews.getIsHidden() > 0);
            nxtStructAdminProductReviewsItem.setOriginType(nxtReviews.getOriginType());
            list.add(nxtStructAdminProductReviewsItem);
        }

        return list;
    }

    /**
     * 获取某个产品的评论列表详情，包含该评论的回复
     * @param offset
     * @param limit
     * @param productId
     * @return
     */
    public List<NxtStructProductReviewsItem> productReviewsItemList(Integer offset, Integer limit, Long productId){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Map<Long,NxtStructProductReviewsItem> mapIdToReviewsItem = new HashMap<>();

        List<NxtStructProductReviewsItem> listAll = new ArrayList<>();


        List<NxtStructProductReviewsItem> list = new ArrayList<>();

        List<NxtReviews> nxtReviewsList = nxtReviewsService.queryUserReviewsByProductId(offset,limit,productId);

        List<Long> userIdList = new ArrayList<>();
        List<Long> reviewsIdList = new ArrayList<>();


        for (NxtReviews item : nxtReviewsList) {

            userIdList.add(item.getUserId());
            reviewsIdList.add(item.getId());

            NxtStructProductReviewsItem nxtStructProductReviewsItem = new NxtStructProductReviewsItem();
            nxtStructProductReviewsItem.setId(item.getId());
            nxtStructProductReviewsItem.setUserId(item.getUserId());
            nxtStructProductReviewsItem.setDate(sdf.format(new Date(item.getDateline())));
            nxtStructProductReviewsItem.setContent(item.getContent());

            mapIdToReviewsItem.put(item.getId(),nxtStructProductReviewsItem);

            list.add(nxtStructProductReviewsItem);

            listAll.add(nxtStructProductReviewsItem);

        }

        //获取回复列表
        if (reviewsIdList.size() > 0){
            List<NxtReviews> allReplyList = nxtReviewsService.queryReviewsReplyByIdList(reviewsIdList);

            for (NxtReviews item :
                    allReplyList) {

                userIdList.add(item.getUserId());
                reviewsIdList.add(item.getId());

                List<NxtStructProductReviewsItem> replyList = mapIdToReviewsItem.get(item.getParentId()).getReplyList();

                NxtStructProductReviewsItem nxtStructProductReviewsItem = new NxtStructProductReviewsItem();
                nxtStructProductReviewsItem.setId(item.getId());
                nxtStructProductReviewsItem.setUserId(item.getUserId());
                nxtStructProductReviewsItem.setDate(sdf.format(new Date(item.getDateline())));
                nxtStructProductReviewsItem.setContent(item.getContent());

                replyList.add(nxtStructProductReviewsItem);

                mapIdToReviewsItem.put(item.getId(),nxtStructProductReviewsItem);

                listAll.add(nxtStructProductReviewsItem);

            }

        }

        //查询所有评论的用户名
        List<NxtUser> userList = nxtUserService.selectByIdSet(0,Integer.MAX_VALUE,userIdList);
        Map<Long,Long> mapUserIdToAvatarId = new HashMap<>();
        Map<Long,NxtUser> mapIdToUser = new HashMap<>();
        for (NxtUser item :
                userList) {
            mapIdToUser.put(item.getId(),item);
            if (item.getAvatarId() != null) {
                mapUserIdToAvatarId.put(item.getId(), item.getAvatarId());
            }
        }

        //查询所有评论的头像
        List<Long> avatarIdList = new ArrayList<>();
        if (mapUserIdToAvatarId.size()>0) {
            avatarIdList.addAll(mapUserIdToAvatarId.values());
        }
        List<NxtUploadfile> uploadfileListAvatar = nxtUploadfileService.selectByIdSet(0,Integer.MAX_VALUE,avatarIdList);
        Map<Long,String> mapAvatarIdToPicUrl = new HashMap<>();
        for (NxtUploadfile item : uploadfileListAvatar) {
            mapAvatarIdToPicUrl.put(item.getId(),nxtUploadImageComponent.convertImagePathToDomainImagePath(item.getUrlpath()));
        }

        //查询所有评论的图片
        List<NxtReviewsPicture> nxtReviewsPictureList = nxtReviewsPictureService.selectByReviewsIdSet(0,Integer.MAX_VALUE,reviewsIdList);
        Map<Long,Long> mapUploadfileIdToReviewsId = new HashMap<>();

        for (NxtReviewsPicture item :
                nxtReviewsPictureList) {
            mapUploadfileIdToReviewsId.put(item.getUploadfileId(), item.getReviewsId());
        }
        List<Long> picIdList = new ArrayList<>();
        if (mapUploadfileIdToReviewsId.size()>0) {
            picIdList.addAll(mapUploadfileIdToReviewsId.keySet());
        }
        List<NxtUploadfile> uploadfileListPic = nxtUploadfileService.selectByIdSet(0,Integer.MAX_VALUE,picIdList);
        Map<Long,List<String>> mapReviewsIdToPicList = new HashMap<>();
        for (NxtUploadfile item : uploadfileListPic) {
            Long reviewsId = mapUploadfileIdToReviewsId.get(item.getId());
            if (!mapReviewsIdToPicList.containsKey(reviewsId)){
                mapReviewsIdToPicList.put(reviewsId,new ArrayList<>());
            }
            List<String> picUrlList = mapReviewsIdToPicList.get(reviewsId);
            picUrlList.add(nxtUploadImageComponent.convertImagePathToDomainImagePath(item.getUrlpath()));
        }

        //给评论加用户名、头像、图片
        for (NxtStructProductReviewsItem structItem : listAll) {
            structItem.setAvatar("/common/images/avatar.jpeg");//默认头像
            structItem.setUsername("****");//默认昵称
            //加昵称
            if (mapIdToUser.containsKey(structItem.getUserId())) {
                structItem.setUsername(mapIdToUser.get(structItem.getUserId()).getUsername());
            }
            //加头像
            if (mapUserIdToAvatarId.containsKey(structItem.getUserId())){
                Long avatarId = mapUserIdToAvatarId.get(structItem.getUserId());
                if (mapAvatarIdToPicUrl.containsKey(avatarId)){
                    structItem.setAvatar(mapAvatarIdToPicUrl.get(avatarId));
                }
            }
            //附加图片
            if (mapReviewsIdToPicList.containsKey(structItem.getId())){
                structItem.setPicUrlList(mapReviewsIdToPicList.get(structItem.getId()));
            }
        }

        return list;

    }

    /**
     * 给某订单详情中的物品加入所有评论详情，包含该评论的回复
     * @param nxtStructOrderForm
     */
    public void queryReviewsPutIntoStructOrderForm(NxtStructOrderForm nxtStructOrderForm){

        Map<Long,NxtStructOrderFormProduct> mapOrderFromProductIdToReviewsItem = new HashMap<>();

        for (NxtStructOrderFormProduct item : nxtStructOrderForm.getOrderFormProductList()){
            mapOrderFromProductIdToReviewsItem.put(item.getId(),item);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Map<Long,NxtStructProductReviewsItem> mapIdToReviewsItem = new HashMap<>();

        List<NxtStructProductReviewsItem> listAll = new ArrayList<>();

        List<NxtStructProductReviewsItem> list = new ArrayList<>();

        List<NxtReviews> nxtReviewsList = nxtReviewsService.queryUserReviewsByOrderFormId(nxtStructOrderForm.getId());

        List<Long> userIdList = new ArrayList<>();
        List<Long> reviewsIdList = new ArrayList<>();


        for (NxtReviews item : nxtReviewsList) {

            userIdList.add(item.getUserId());
            reviewsIdList.add(item.getId());

            NxtStructProductReviewsItem nxtStructProductReviewsItem = new NxtStructProductReviewsItem();
            nxtStructProductReviewsItem.setId(item.getId());
            nxtStructProductReviewsItem.setUserId(item.getUserId());
            nxtStructProductReviewsItem.setDate(sdf.format(new Date(item.getDateline())));
            nxtStructProductReviewsItem.setContent(item.getContent());
            nxtStructProductReviewsItem.setOriginType(item.getOriginType());

            mapIdToReviewsItem.put(item.getId(),nxtStructProductReviewsItem);

            list.add(nxtStructProductReviewsItem);

            listAll.add(nxtStructProductReviewsItem);

            if (mapOrderFromProductIdToReviewsItem.containsKey(item.getOrderFormProductId())){
                mapOrderFromProductIdToReviewsItem.get(item.getOrderFormProductId()).setReviewsItem(nxtStructProductReviewsItem);
            }

        }

        //获取回复列表
        if (reviewsIdList.size() > 0){
            List<NxtReviews> allReplyList = nxtReviewsService.queryReviewsReplyByIdList(reviewsIdList);

            for (NxtReviews item :
                    allReplyList) {

                userIdList.add(item.getUserId());
                reviewsIdList.add(item.getId());

                List<NxtStructProductReviewsItem> replyList = mapIdToReviewsItem.get(item.getParentId()).getReplyList();

                NxtStructProductReviewsItem nxtStructProductReviewsItem = new NxtStructProductReviewsItem();
                nxtStructProductReviewsItem.setId(item.getId());
                nxtStructProductReviewsItem.setUserId(item.getUserId());
                nxtStructProductReviewsItem.setDate(sdf.format(new Date(item.getDateline())));
                nxtStructProductReviewsItem.setContent(item.getContent());
                nxtStructProductReviewsItem.setOriginType(item.getOriginType());

                replyList.add(nxtStructProductReviewsItem);

                mapIdToReviewsItem.put(item.getId(),nxtStructProductReviewsItem);

                listAll.add(nxtStructProductReviewsItem);

            }

        }

        //查询所有评论的用户名
        List<NxtUser> userList = nxtUserService.selectByIdSet(0,Integer.MAX_VALUE,userIdList);
        Map<Long,Long> mapUserIdToAvatarId = new HashMap<>();
        Map<Long,NxtUser> mapIdToUser = new HashMap<>();
        for (NxtUser item :
                userList) {
            mapIdToUser.put(item.getId(),item);
            if (item.getAvatarId() != null) {
                mapUserIdToAvatarId.put(item.getId(), item.getAvatarId());
            }
        }

        //查询所有评论的头像
        List<Long> avatarIdList = new ArrayList<>();
        if (mapUserIdToAvatarId.size()>0) {
            avatarIdList.addAll(mapUserIdToAvatarId.values());
        }
        List<NxtUploadfile> uploadfileListAvatar = nxtUploadfileService.selectByIdSet(0,Integer.MAX_VALUE,avatarIdList);
        Map<Long,String> mapAvatarIdToPicUrl = new HashMap<>();
        for (NxtUploadfile item : uploadfileListAvatar) {
            mapAvatarIdToPicUrl.put(item.getId(),nxtUploadImageComponent.convertImagePathToDomainImagePath(item.getUrlpath()));
        }

        //查询所有评论的图片
        List<NxtReviewsPicture> nxtReviewsPictureList = nxtReviewsPictureService.selectByReviewsIdSet(0,Integer.MAX_VALUE,reviewsIdList);
        Map<Long,Long> mapUploadfileIdToReviewsId = new HashMap<>();

        for (NxtReviewsPicture item :
                nxtReviewsPictureList) {
            mapUploadfileIdToReviewsId.put(item.getUploadfileId(), item.getReviewsId());
        }
        List<Long> picIdList = new ArrayList<>();
        if (mapUploadfileIdToReviewsId.size()>0) {
            picIdList.addAll(mapUploadfileIdToReviewsId.keySet());
        }
        List<NxtUploadfile> uploadfileListPic = nxtUploadfileService.selectByIdSet(0,Integer.MAX_VALUE,picIdList);
        Map<Long,List<String>> mapReviewsIdToPicList = new HashMap<>();
        for (NxtUploadfile item : uploadfileListPic) {
            Long reviewsId = mapUploadfileIdToReviewsId.get(item.getId());
            if (!mapReviewsIdToPicList.containsKey(reviewsId)){
                mapReviewsIdToPicList.put(reviewsId,new ArrayList<>());
            }
            List<String> picUrlList = mapReviewsIdToPicList.get(reviewsId);
            picUrlList.add(nxtUploadImageComponent.convertImagePathToDomainImagePath(item.getUrlpath()));
        }

        //给评论加用户名、头像、图片
        for (NxtStructProductReviewsItem structItem : listAll) {
            structItem.setAvatar("/common/images/avatar.jpeg");//默认头像
            structItem.setUsername("****");//默认昵称
            //加昵称
            if (mapIdToUser.containsKey(structItem.getUserId())) {
                structItem.setUsername(mapIdToUser.get(structItem.getUserId()).getUsername());
            }
            //加头像
            if (mapUserIdToAvatarId.containsKey(structItem.getUserId())){
                Long avatarId = mapUserIdToAvatarId.get(structItem.getUserId());
                if (mapAvatarIdToPicUrl.containsKey(avatarId)){
                    structItem.setAvatar(mapAvatarIdToPicUrl.get(avatarId));
                }
            }
            //附加图片
            if (mapReviewsIdToPicList.containsKey(structItem.getId())){
                structItem.setPicUrlList(mapReviewsIdToPicList.get(structItem.getId()));
            }
        }

    }

    /**
     * 用户创建订单评论
     * @param userId
     */
    @Transactional(rollbackFor=Exception.class)
    public void create(Long userId, NxtStructOrderFormReivewsCreate nxtStructOrderFormReivewsCreate) throws NxtException {

        Long orderFormId = nxtStructOrderFormReivewsCreate.getOrderFormId();
        Long orderFormProductId = nxtStructOrderFormReivewsCreate.getOrderFormProductId();
        String content = nxtStructOrderFormReivewsCreate.getContent();

        if (orderFormId == null){
            throw new NxtException("请提供订单号");
        }
        if (orderFormProductId == null){
            throw new NxtException("请提供订单物品号");
        }
        if (content == null || content.isEmpty()){
            throw new NxtException("请填写内容");
        }

        NxtOrderForm nxtOrderForm = nxtOrderFormService.queryById(orderFormId);

        if (nxtOrderForm == null){
            throw new NxtException("找不到该订单");
        }
        if (!nxtOrderForm.getUserId().equals(userId)){
            throw new NxtException("该订单不属于该用户");
        }

        NxtOrderFormProduct nxtOrderFormProduct = nxtOrderFormProductService.queryById(orderFormProductId);

        if (nxtOrderFormProduct == null){
            throw new NxtException("找不到该订单物品");
        }
        if (!nxtOrderFormProduct.getOrderFormId().equals(nxtOrderForm.getId())){
            throw new NxtException("该订单物品不属于该订单");
        }

        //查询有没有根评论
        NxtReviews rootReviews = nxtReviewsService.queryRootReviewsByOrderFormProductId(orderFormProductId);

        if (rootReviews != null) {
            Integer countUserReply = 0;//用户追评数量
            //查询所有回复
            List<Long> rootReviewsIdList = new ArrayList<>();
            rootReviewsIdList.add(rootReviews.getId());
            List<NxtReviews> userReplayReviews = nxtReviewsService.queryReviewsReplyByIdList(rootReviewsIdList);
            for (NxtReviews item :
                    userReplayReviews) {
                if (item.getOriginType().equals(0) && item.getParentId().equals(rootReviews.getId())) {
                    countUserReply++;
                }
            }
            if (countUserReply > 0){
                throw new NxtException("该订单物品已经追评过一次，不能再评");
            }
        }

        NxtReviews nxtReviews = new NxtReviews();
        nxtReviews.setOriginType(0);//0:用户评 1:管理员回复
        nxtReviews.setUserId(userId);
        nxtReviews.setProductId(nxtOrderFormProduct.getProductId());
        nxtReviews.setOrderFormId(orderFormId);
        nxtReviews.setOrderFormProductId(orderFormProductId);
        nxtReviews.setDateline(System.currentTimeMillis());
        nxtReviews.setContent(content);
        nxtReviews.setParentId(0L);
        nxtReviews.setIsRecommend(0);
        nxtReviews.setIsHidden(1);//用户新增评论，默认隐藏

        if (rootReviews != null){
            nxtReviews.setParentId(rootReviews.getId());
            nxtReviews.setIsHidden(0);//回复无所谓隐藏不隐藏，根评论不显示，回复当然看不见
        }

        nxtReviewsService.insert(nxtReviews);

        //图片id关联
        List<Long> imageIdList = nxtStructOrderFormReivewsCreate.getImageIdList();
        if (imageIdList != null) {
            for (Long uploadFileId : imageIdList) {
                NxtReviewsPicture nxtReviewsPicture = nxtReviewsPictureService.queryByUploadFileId(uploadFileId);
                if (nxtReviewsPicture != null){
                    throw new NxtException("其中一张图片已经被评论用过");
                }
                NxtUploadfile nxtUploadfile = nxtUploadfileService.queryById(uploadFileId);
                if (nxtUploadfile == null){
                    throw new NxtException("其中一张图片id无效");
                }
            }
            for (Long uploadFileId : imageIdList) {
                NxtReviewsPicture nxtReviewsPicture = new NxtReviewsPicture();
                nxtReviewsPicture.setReviewsId(nxtReviews.getId());
                nxtReviewsPicture.setUploadfileId(uploadFileId);
                nxtReviewsPictureService.insert(nxtReviewsPicture);
            }
        }

    }

}
