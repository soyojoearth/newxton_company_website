package com.newxton.nxtframework.controller.api.front.callback;

import com.google.gson.Gson;
import com.newxton.nxtframework.component.NxtPaymentAlipayComponent;
import com.newxton.nxtframework.component.NxtUtilComponent;
import com.newxton.nxtframework.entity.*;
import com.newxton.nxtframework.exception.NxtException;
import com.newxton.nxtframework.process.NxtProcessFinishRechargeCallback;
import com.newxton.nxtframework.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/12
 * @address Shenzhen, China
 * @copyright NxtFramework
 * 支付宝回调通知
 */
@RestController
public class NxtApiCallbackAlipayController {

    private Logger logger = LoggerFactory.getLogger(NxtApiCallbackAlipayController.class);

    @Resource
    private HttpServletRequest request;

    @Resource
    private NxtRechargeService nxtRechargeService;

    @Resource
    private NxtProcessFinishRechargeCallback nxtProcessFinishRechargeCallback;

    @Resource
    private NxtPaymentAlipayComponent nxtPaymentAlipayComponent;

    @Resource
    private NxtUtilComponent nxtUtilComponent;

    @RequestMapping("/api/callback/alipay")
    public String exec() throws Exception {

        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = nxtPaymentAlipayComponent.verifyNotify(params);

        if(signVerified){

            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //支付宝交易号trade_status
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
            //实收金额
            String receipt_amount = new String(request.getParameter("receipt_amount").getBytes("ISO-8859-1"),"UTF-8");

            float amountFloat;

            try {
                amountFloat = Float.parseFloat(receipt_amount);
            }
            catch(NumberFormatException ex) {
                logger.info("Alipay callback 金额转换错误");
                logger.info(ex.getMessage());
                amountFloat = 0.0F;
            }

            Long amount = (long) (amountFloat * 100F);

            if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                NxtRecharge nxtRecharge = nxtRechargeService.queryBySerialNum(out_trade_no);
                if (nxtRecharge == null){
                    return "failure";//没有该笔订单
                }
                //如果有做过处理，不执行商户的业务程序
                if (!nxtRecharge.getStatus().equals(0)){
                    //已处理过，收到重复通知
                    return "success";
                }
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                try{
                    nxtProcessFinishRechargeCallback.didComplete(nxtRecharge.getSerialNum(),amount,"alipay",trade_no,new Gson().toJson(params));
                }
                catch (NxtException e){
                    logger.warn("支付宝callback失败："+e.getNxtExecptionMessage()+" 数据："+new Gson().toJson(params));
                    return "failure";
                }
            }
            return "success";
        }else{
            // 验签失败则记录异常日志，并在response中返回failure.
            logger.warn("支付宝验签失败，数据："+new Gson().toJson(params));
            return "failure";
        }

    }

}
