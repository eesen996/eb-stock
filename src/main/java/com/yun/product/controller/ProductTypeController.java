package com.yun.product.controller;

import com.yun.product.model.Product;
import com.yun.product.model.ProductType;
import com.yun.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
public class ProductTypeController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/productTypeList")
    public String productTypeList(Model model){
        /*List<ProductType> productTypeList=productService.findProductTypeList();
        model.addAttribute("productTypeList",productTypeList);*/
        log.info("跳转到stock/productTypeList.html页面");
        return "product/productTypeList";
    }

    @RequestMapping("/productTypeTree")
    @ResponseBody
    public List<ProductType> productTypeTree(){
        List<ProductType> productTypeList=productService.findProductTypeRoot(0);
        return productTypeList;
    }

    @RequestMapping("/productTypeEdit")
    public String productTypeEdit(){
        return "product/productTypeEdit";
    }

    @RequestMapping("/productTypeUpdate")
    public String productTypeUpdate(Integer prodTypeId,Model model){
        ProductType productType=productService.findProductTypeById(prodTypeId);
        model.addAttribute("prodType",productType);
        return "product/productTypeUpdate";
    }

    @RequestMapping("/productTypeSave")
    public String productTypeSave(ProductType productType) {
        productService.insertProductType(productType);
        return "redirect:productTypeList";
    }

    @RequestMapping("/productTypeUpdateSave")
    public String productTypeUpdateSave(ProductType productType) {
        productService.updateProductType(productType);
        return "redirect:productTypeList";
    }

    @RequestMapping("/productTypeDelete")
    public String productTypeDelete(Integer prodTypeId, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        ProductType productType=productService.findProductTypeById(prodTypeId);
        if(productType.getChildNodeList().size()>0){
            //如果有子类型不能删除
            response.getWriter().print("<script>alert('该类别下还有子目录，请先删除子目录再执行删除');window.history.go(-1)</script>");
            return null;
        }else {
            //子类型如果被物品使用，也不能删除
            List<Product> productList=productService.findProductByProdType(prodTypeId);
            if(productList.size()>0){
                response.getWriter().print("<script>alert('该类别已经被物品数据引用，请先删除相关物品数据再执行删除');window.history.go(-1)</script>");
                return null;
            }
            productService.deleteProductType(prodTypeId);
        }
        return "redirect:productTypeList";
    }
}
