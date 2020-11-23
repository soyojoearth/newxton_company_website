package com.newxton.nxtframework.process;

import com.newxton.nxtframework.component.NxtUploadImageComponent;
import com.newxton.nxtframework.entity.NxtReviews;
import com.newxton.nxtframework.entity.NxtReviewsPicture;
import com.newxton.nxtframework.entity.NxtUploadfile;
import com.newxton.nxtframework.entity.NxtUser;
import com.newxton.nxtframework.service.NxtReviewsPictureService;
import com.newxton.nxtframework.service.NxtReviewsService;
import com.newxton.nxtframework.service.NxtUploadfileService;
import com.newxton.nxtframework.service.NxtUserService;
import com.newxton.nxtframework.struct.NxtStructProductReviewsItem;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/23
 * @address Shenzhen, China
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

}
