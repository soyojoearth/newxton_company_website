package com.newxton.nxtframework.controller.web.install;

import com.newxton.nxtframework.schedule.task.init.*;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/23
 * @address Shenzhen, China
 */
@Controller
public class NxtInstallController {

    @RequestMapping("/install")
    public ModelAndView index(Device device, ModelAndView model) throws Exception {
        this.processInitConfigAndData();
        model.setViewName("install");
        return model;
    }

    @Resource
    private NxtTaskCheckAndInitPages nxtTaskCheckAndInitPages;

    @Resource
    private NxtTaskCheckAndInitAclAction nxtTaskCheckAndInitAclAction;

    @Resource
    private NxtTaskCheckAndInitAdminUser nxtTaskCheckAndInitAdminUser;

    @Resource
    private NxtTaskCheckAndInitBannerData nxtTaskCheckAndInitBannerData;

    @Resource
    private NxtTaskCheckAndInitDeliveryRegion nxtTaskCheckAndInitDeliveryRegion;

    @Resource
    private NxtTaskCheckAndInitNewsCategory nxtTaskCheckAndInitNewsCategory;

    @Resource
    private NxtTaskCheckAndInitProductCategory nxtTaskCheckAndInitProductCategory;

    @Resource
    private NxtTaskCheckAndInitUserLevel nxtTaskCheckAndInitUserLevel;

    /**
     * 系统刚部署的时候，检查初始化数据
     * @throws Exception
     */
    public void processInitConfigAndData() throws Exception {

        //检查&初始化acl_action
        nxtTaskCheckAndInitAclAction.exec();

        //检查&创建 admin 账户 & 赋予 所有管理权限
        nxtTaskCheckAndInitAdminUser.exec();

        //检查&初始化默认页面数据
        nxtTaskCheckAndInitPages.exec();

        //检查&初始化默认Banner图
        nxtTaskCheckAndInitBannerData.exec();

        //检查&初始化资讯类别
        nxtTaskCheckAndInitNewsCategory.exec();

        //检查&初始化产品类别
        nxtTaskCheckAndInitProductCategory.exec();

        //检查&初始化地区列表
        nxtTaskCheckAndInitDeliveryRegion.exec();

        //检查&初始化用户等级数据
        nxtTaskCheckAndInitUserLevel.exec();

        //检查&初始化示例资讯

        //检查&初始化示例产品

    }

}
