package com.yun.stock.controller;

import com.yun.common.Page;
import com.yun.stock.model.ProductReceiveStock;
import com.yun.stock.model.ProductStock;
import com.yun.stock.service.ProductStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class ProductStockController {

    @Autowired
    private ProductStockService productStockService;

    @RequestMapping("/productStockList")
    public String productStocklist(String prodName,String pageNo,Model model){
        log.debug("跳转到物品库存查询控制器");
        Map<String,Object> param=new HashMap<>();
        if(null!=prodName&&!"".equals(prodName)){
            param.put("prodName","%"+prodName+"%");
        }

        Page page=new Page();
        int row=productStockService.findProductStockRowCnt(param);
        page.setRow(row);
        page.setPageSize(5);
        if(null==pageNo||"".equals(pageNo)){
            pageNo="1";
        }
        page.setPageNo(Integer.parseInt(pageNo));

        List<ProductStock> productStockList=productStockService.findProductStockList(param,page);
        model.addAttribute("productStockList",productStockList);
        model.addAttribute("page",page);
        model.addAttribute("prodName",prodName);
        log.debug("转到物品库存页面");
        return "stock/productStockList";
    }

    @RequestMapping("/productStockPreAdd")
    public String productStockPreAdd(){
        return "stock/productStockAdd";
    }

    @RequestMapping("/productStockSave")
    public void productStockSave(ProductStock productStock, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();
        int prodId=productStock.getProduct().getProdId();
        int prodParamId=productStock.getProductParameter().getProdParamId();
        List<ProductStock> list=productStockService.findProductStockRecordByProdIdAndProdParamId(prodId,prodParamId);
        if(list.size()>0){
            out.print("<script>alert('添加库存新物品"+prodId+"和分类参数"+prodParamId+"必须保证唯一，不能重复');window.history.go(-1);</script>");
            return;
        }
        productStockService.insertProductStock(productStock);
        out.print("<script>alert('添加库存新物品操作完成');window.location='productStockList'</script>");
    }

    @RequestMapping("/productStockDelete")
    public String productStockDelete(int prodStockId){
        productStockService.deleteProductStock(prodStockId);
        return "redirect:productStockList";
    }

    @RequestMapping("/productStockPreUpdate")
    public String productStockPreUpdate(int prodStockId,Model model){
        ProductStock productStock=productStockService.findProductStockById(prodStockId);
        model.addAttribute("productStock",productStock);
        return "stock/productStockUpdate";
    }

    @RequestMapping("/productStockUpdate")
    public void productStockUpdate(ProductStock productStock,HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();
        int prodId=productStock.getProduct().getProdId();
        int prodParamId=productStock.getProductParameter().getProdParamId();
        List<ProductStock> list=productStockService.findProductStockRecordByProdIdAndProdParamId(prodId,prodParamId);
        //如果prodId和prodParamId查询出不止一个重复的记录，数据本身有错误
        if(list.size()>1){
            out.print("<script>alert('数据错误，修改现有库存物品资料"+prodId+"和分类参数"+prodParamId+"必须保证唯一，不能重复');window.history.go(-1);</script>");
            return;
        }
        //如果只查询到一条记录，再判断是否正好是当前要修改的记录，
        // 如果id不同，那么现在打算修改的记录和原有的记录有重复
        if(list.size()==1){
            ProductStock dbStock=list.get(0);
            if(dbStock.getProdStockId()!=productStock.getProdStockId()){
                out.print("<script>alert('修改现有库存物品资料"+prodId+"和分类参数"+prodParamId+"必须保证唯一，不能重复');window.history.go(-1);</script>");
                return;
            }
        }
        productStockService.updateProductStock(productStock);
        out.print("<script>alert('修改现有库存物品资料完成');window.location='productStockList'</script>");

    }

    
}
