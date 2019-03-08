package com.yun.stock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yun.common.JsonHelper;
import com.yun.common.Page;
import com.yun.product.model.ProductParameter;
import com.yun.product.service.ProductService;
import com.yun.stock.model.ProductReceiveStock;
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
public class ProductReceiveStockController {

    @Autowired
    private ProductStockService productStockService;

    @RequestMapping("/productReceiveStockList")
    public String productReceiveStocklist(String prodName,String pageNo,Model model){
        log.debug("跳转到待收库存查询控制器");
        Map<String,Object> param=new HashMap<>();
        if(null!=prodName&&!"".equals(prodName)){
            param.put("prodName","%"+prodName+"%");
        }

        Page page=new Page();
        int row=productStockService.findProductReceiveStockRowCnt(param);
        page.setRow(row);
        page.setPageSize(5);
        if(null==pageNo||"".equals(pageNo)){
            pageNo="1";
        }
        page.setPageNo(Integer.parseInt(pageNo));

        List<ProductReceiveStock> productReceiveStockList=productStockService.findProductReceiveStockList(param,page);
        model.addAttribute("productReceiveStockList",productReceiveStockList);
        model.addAttribute("page",page);
        model.addAttribute("prodName",prodName);
        log.debug("转到待收库存页面");
        return "stock/productReceiveStockList";
    }

    @RequestMapping("/productReceiveStockPreAdd")
    public String productReceiveStockPreAdd(){
        return "stock/productReceiveStockAdd";
    }

    @RequestMapping("/productReceiveStockSave")
    public void productReceiveStockSave(ProductReceiveStock productReceiveStock, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();
        int prodId=productReceiveStock.getProduct().getProdId();
        int prodParamId=productReceiveStock.getProductParameter().getProdParamId();
        List<ProductReceiveStock> list=productStockService.findProductReceiveStockRecordByProdIdAndProdParamId(prodId,prodParamId);
        if(list.size()>0){
            out.print("<script>alert('添加新物品"+prodId+"和分类参数"+prodParamId+"必须保证唯一，不能重复');window.history.go(-1);</script>");
            return;
        }
        productStockService.insertProductReceiveStock(productReceiveStock);
        out.print("<script>alert('添加新的物品操作完成');window.location='productReceiveStockList'</script>");
    }

    @RequestMapping("/productReceiveStockDelete")
    public String productReceiveStockDelete(int prodReceStockId){
        productStockService.deleteProductReceiveStock(prodReceStockId);
        return "redirect:productReceiveStockList";
    }

    @RequestMapping("/productReceiveStockPreUpdate")
    public String productReceiveStockPreUpdate(int prodReceStockId,Model model){
        ProductReceiveStock productReceiveStock=productStockService.findProductReceiveStockById(prodReceStockId);
        model.addAttribute("productReceStock",productReceiveStock);
        return "stock/productReceiveStockUpdate";
    }

    @RequestMapping("/productReceiveStockUpdate")
    public void productReceiveStockUpdate(ProductReceiveStock productReceiveStock,HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();
        int prodId=productReceiveStock.getProduct().getProdId();
        int prodParamId=productReceiveStock.getProductParameter().getProdParamId();
        List<ProductReceiveStock> list=productStockService.findProductReceiveStockRecordByProdIdAndProdParamId(prodId,prodParamId);
        //如果prodId和prodParamId查询出不止一个重复的记录，数据本身有错误
        if(list.size()>1){
            out.print("<script>alert('数据错误，修改待收库存物品资料"+prodId+"和分类参数"+prodParamId+"必须保证唯一，不能重复');window.history.go(-1);</script>");
            return;
        }
        //如果只查询到一条记录，再判断是否正好是当前要修改的记录，
        // 如果id不同，那么现在打算修改的记录和原有的记录有重复
        if(list.size()==1){
            ProductReceiveStock dbReceiveStock=list.get(0);
            if(dbReceiveStock.getProdReceStockId()!=productReceiveStock.getProdReceStockId()){
                out.print("<script>alert('修改待收库存物品资料"+prodId+"和分类参数"+prodParamId+"必须保证唯一，不能重复');window.history.go(-1);</script>");
                return;
            }
        }
        productStockService.updateProductReceiveStock(productReceiveStock);
        out.print("<script>alert('修改待收库存物品资料完成');window.location='productReceiveStockList'</script>");
    }

}
