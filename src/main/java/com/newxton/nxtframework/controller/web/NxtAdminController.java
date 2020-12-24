package com.newxton.nxtframework.controller.web;

import com.newxton.nxtframework.component.NxtWebUtilComponent;
import com.newxton.nxtframework.schedule.task.NxtTaskCommission;
import com.newxton.nxtframework.schedule.task.NxtTaskOrderForm;
import com.newxton.nxtframework.schedule.task.init.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/21
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
@Controller
public class NxtAdminController {

    @Resource
    NxtWebUtilComponent nxtWebUtilComponent;

    @Resource
    private NxtTaskOrderForm nxtTaskOrderForm;

    @Resource
    private NxtTaskCommission nxtTaskCommission;

    /**
     * 管理后台技术采用前后端分离，前端使用Vue，后端调用/api/接口
     * @param model
     * @return
     */
    @RequestMapping("/admin")
    public ModelAndView index(ModelAndView model) {

        if (nxtWebUtilComponent.isMSBrowser()){
            //后台不支持微软的浏览器：IE和Edge
            model.setViewName("admin_no_ie");
        }
        else {
            //这里直接加载前端工程师的打包页面即可，没其他啥事
            model.setViewName("admin");
        }

        //管理员每次刷新后台的时候乘机执行：检查订单自动确认收货、检查佣金自动到账
        processDoSomeTask();

        return model;

    }

    /**
     * 一些原本做成Schedule方式执行的Cronjob，但是后来觉得杀鸡用牛刀，这些任务还是放在这里，用Http带动无状态执行比较好
     * 集群下部署也不会有多实例同时执行，
     * 有些地方，不要为了技术而技术，简单好用就挺好
     */
    private void processDoSomeTask(){

        //检查&自动确认收货
        nxtTaskOrderForm.checkOrderFormAndComfirmReceived();

        //检查&执行佣金完成，可以结转
        nxtTaskCommission.checkCommissionAndComfirmCompleted();

    }

}
