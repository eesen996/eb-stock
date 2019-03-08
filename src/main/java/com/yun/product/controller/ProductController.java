package com.yun.product.controller;

import com.yun.common.Page;
import com.yun.product.model.Product;
import com.yun.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 物品列表查询
     */
    @RequestMapping("/productList")
    public String productList(Model model,String pageNo,String prodName,String prodNo,String prodTypeName){

        Map<String,Object> param=new HashMap<>();
        if(null!=prodName&&!"".equals(prodName)){
            param.put("prodName","%"+prodName+"%");
        }
        if(null!=prodNo&&!"".equals(prodNo)){
            param.put("prodNo","%"+prodNo+"%");
        }
        if(null!=prodTypeName&&!"".equals(prodTypeName)){
            param.put("prodTypeName","%"+prodTypeName+"%");
        }
        Page page=new Page();
        int row=productService.findProductRowByParam(param);
        page.setRow(row);
        page.setPageSize(5);
        if(null==pageNo||"".equals(pageNo)){
            pageNo="1";
        }
        page.setPageNo(Integer.parseInt(pageNo));

        List<Product> productList=productService.findProductByParam(param,page);
        model.addAttribute("page",page);
        model.addAttribute("productList",productList);
        model.addAttribute("prodName",prodName);
        model.addAttribute("prodNo",prodNo);
        model.addAttribute("prodTypeName",prodTypeName);
        return "product/productList";
    }

    /**
     * 物品资料新增前
     */
    @RequestMapping("/productPreAdd")
    public String productPreAdd(){
        return "product/productAdd";
    }

    /**
     * 物品资料新增保存
     */
    @RequestMapping("/productSave")
    public String productSave(Product product){
        productService.insertProduct(product);
        return "redirect:productList";
    }

    @RequestMapping("/productDelete")
    public String productDelete(int productId) {
            productService.deleteProduct(productId);
            return "redirect:productList";
    }



    /**
     * 物品资料修改前
     */
    @RequestMapping("/productPreUpdate")
    public String productPreUpdate(int productId,Model model){
        Product product=productService.findProductById(productId);
        model.addAttribute("product",product);
        return "product/productUpdate";
    }

    /**
     * 物品资料修改保存
     */
    @RequestMapping("/productUpdate")
    public String productUpdate(Product product){
        productService.updateProduct(product);
        return "redirect:productList";
    }

    /**
     * 物品资料自动补齐需要的Ajax调用
     */
    @RequestMapping("/productListForAutoComplete")
    @ResponseBody
    public List<Product> productListForAutoComplete(String prodName){
        Map<String,Object> param=new HashMap<>();
        if(null!=prodName&&!"".equals(prodName)) {
            param.put("prodName", "%" + prodName + "%");
        }
        List<Product> list=productService.findProductByParam(param,null);
        return list;
    }

    /**
     * 物品库存数量初始化获得物品列表的ajax调用
     * @return
     */
    @RequestMapping("/productListForStock")
    @ResponseBody
    public List<Product> productListForStock(){
        return productService.productListForStock();
    }

    /**
     * 物品交易时获得物品列表的调用
     * @return
     */
    @RequestMapping("/productListForTrade")
    @ResponseBody
    public List<Product> productListForTrade(String prodName){
        Map<String,Object> param=new HashMap<>();
        if(null!=prodName&&!"".equals(prodName)) {
            param.put("prodName", "%" + prodName + "%");
        }
        List<Product> list=productService.findProductByParam(param,null);
        return list;
    }
}
