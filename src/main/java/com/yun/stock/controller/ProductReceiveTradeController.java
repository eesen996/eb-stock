package com.yun.stock.controller;

import com.yun.common.Constract;
import com.yun.common.DateHelper;
import com.yun.stock.model.ProductReceiveStock;
import com.yun.stock.model.ProductReceiveTrade;
import com.yun.stock.service.ProductReceiveTradeService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Controller
public class ProductReceiveTradeController {

    @Autowired
    private ProductReceiveTradeService productReceiveTradeService;

    @Autowired
    private ProductStockService productStockService;

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder){
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),true));
    }


    /**
     * 待收入库登记页面
     */
    @RequestMapping("/productReceiveIn")
    public String receiveIn(Model model){
        model.addAttribute("tradeDate",new Date());
        return "stock/productReceiveIn";
    }

    /**
     * 待收入库登记保存
     */
    @RequestMapping("/productReceiveInSave")
    @ResponseBody
    public void receiveInSave(ProductReceiveTrade productReceiveTrade, HttpSession session, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();
        try {
            productReceiveTrade.setOper((UserInfo) session.getAttribute(Constract.USER_SESSION_KEY));
            String result=productReceiveTradeService.insertProductReceiveInTrade(productReceiveTrade);
            out.print("<script>alert('"+result+"');window.location='productReceiveIn'</script>");
        }catch (Exception e){
            log.debug("保存待收入库记录出错："+e);
            out.print("<script>alert('"+e.getMessage()+"');window.history.go(-1)</script>");
        }
    }

    /**
     * 待收入库登记退货
     */
    @RequestMapping("/productReceiveReject")
    public String receiveReject(Model model){
        model.addAttribute("tradeDate",new Date());
        return "stock/productReceiveReject";
    }

    /**
     * 待收入库登记保存
     */
    @RequestMapping("/productReceiveRejectSave")
    @ResponseBody
    public void receiveRejectSave(ProductReceiveTrade productReceiveTrade, HttpSession session, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();
        try {
            productReceiveTrade.setOper((UserInfo) session.getAttribute(Constract.USER_SESSION_KEY));
            String result=productReceiveTradeService.insertProductReceiveRejectTrade(productReceiveTrade);
            log.debug("result:"+result);
            out.print("<script>alert('"+result+"');window.location='productReceiveReject'</script>");
        }catch (Exception e){
            log.debug("保存待收退货记录出错："+e);
            out.print("<script>alert('"+e.getMessage()+"');window.history.go(-1)</script>");
        }
    }

    /**
     * 转到待收转入库存页面
     */
    @RequestMapping("/productReceiveStockToCurrentStock")
    public String productReceiveStockToCurrentStock(int prodReceStockId,Model model){
        ProductReceiveStock productReceiveStock=productStockService.findProductReceiveStockById(prodReceStockId);
        model.addAttribute("productReceiveStock",productReceiveStock);
        model.addAttribute("tradeDate",new Date());
        return "stock/productReceiveToCurrent";
    }

    @RequestMapping("/productReceiveToCurrentSave")
    public void productReceiveToCurrentSave(HttpServletRequest request,HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();

        try {
            UserInfo userInfo=(UserInfo)request.getSession().getAttribute(Constract.USER_SESSION_KEY);
            String strTradeDate=request.getParameter("tradeDate");
            Date tradeDate=DateHelper.string2Date(strTradeDate,"yyyy-MM-dd HH:mm:ss");
            int prodReceStockId=Integer.parseInt(request.getParameter("prodReceStockId"));
            int stockNum=Integer.parseInt(request.getParameter("stockNum"));
            int tradeNum=Integer.parseInt(request.getParameter("tradeNum"));
            if(tradeNum>stockNum){
                throw new Exception("待收库存转入当前库存数量不足");
            }
            String result=productStockService.updateReceiveToCurrentStock(tradeDate,prodReceStockId,tradeNum,userInfo);
            log.debug("result:"+result);
            out.print("<script>alert('"+result+"');window.location='productReceiveStockList'</script>");
        } catch (Exception e){
            log.debug(""+e);
            out.print("<script>alert('"+e.getMessage()+"');window.history.go(-1)</script>");
        }
    }
}
