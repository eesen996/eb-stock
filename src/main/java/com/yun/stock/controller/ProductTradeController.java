package com.yun.stock.controller;

import com.yun.common.Constract;
import com.yun.stock.model.ProductReceiveTrade;
import com.yun.stock.model.ProductTrade;
import com.yun.stock.service.ProductTradeService;
import com.yun.stock.service.ProductStockService;
import com.yun.user.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Controller
public class ProductTradeController {

    @Autowired
    private ProductStockService productStockService;

    @Autowired
    private ProductTradeService productTradeService;

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder){
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),true));
    }

    /**
     * 待收入库登记页面
     */
    @RequestMapping("/productCurrentIn")
    public String productCurrentIn(Model model){
        model.addAttribute("tradeDate",new Date());
        return "stock/productCurrentIn";
    }

    /**
     * 现有入库登记保存
     */
    @RequestMapping("/productCurrentInSave")
    @ResponseBody
    public void productCurrentInSave(ProductTrade productTrade, HttpSession session, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();
        try {
            productTrade.setOper((UserInfo) session.getAttribute(Constract.USER_SESSION_KEY));
            String result=productTradeService.insertProductInTrade(productTrade);
            out.print("<script>alert('"+result+"');window.location='productCurrentIn'</script>");
        }catch (Exception e){
            log.debug("保存现有库存入库记录出错："+e);
            out.print("<script>alert('"+e.getMessage()+"');window.history.go(-1)</script>");
        }
    }

    /**
     * 现有库存登记退货
     */
    @RequestMapping("/productCurrentOut")
    public String receiveReject(Model model){
        model.addAttribute("tradeDate",new Date());
        return "stock/productCurrentOut";
    }

    /**
     * 待收入库登记保存
     */
    @RequestMapping("/productCurrentOutSave")
    @ResponseBody
    public void productCurrentOutSave(ProductTrade productTrade, HttpSession session, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();
        try {
            productTrade.setOper((UserInfo) session.getAttribute(Constract.USER_SESSION_KEY));
            String result=productTradeService.insertProductOutTrade(productTrade);
            log.debug("result:"+result);
            out.print("<script>alert('"+result+"');window.location='productCurrentOut'</script>");
        }catch (Exception e){
            log.debug("保存现有库存退货记录出错："+e);
            out.print("<script>alert('"+e.getMessage()+"');window.history.go(-1)</script>");
        }
    }
}
