package com.yun.product.controller;

import com.yun.common.JsonHelper;
import com.yun.product.model.ProductParameter;
import com.yun.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class ProductParamController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/prodParamList")
    @ResponseBody
    public String prodParamList(int prodId)  {
        List<ProductParameter> prodParamList=productService.findProductParameterListByProductId(prodId);
        try {
            if(prodParamList.size()>0){
                return JsonHelper.getJsonString(1,"查询成功",prodParamList);
            }else{
                return JsonHelper.getJsonString(0,"该物品没有分类列表记录");
            }
        } catch (Exception e) {
            return JsonHelper.getJsonString(-1,"获得物品参数分类列表信息出错");
        }
    }

    @RequestMapping("/prodParamPreAdd")
    public String prodParamPreAdd(int prodId,Model model){
        model.addAttribute("prodId",prodId);
        return "product/productParameterAdd";
    }

    @RequestMapping("/prodParamSave")
    public String prodParamSave(int prodId,String[] prodParamName){
        productService.insertProductParameter(prodId,prodParamName);
        return "redirect:productList";
    }

    @RequestMapping("/prodParamDelete")
    public String prodParamDelete(int prodParamId){
        productService.deleteProductParameter(prodParamId);
        return "redirect:productList";
    }
    @RequestMapping("/prodParamDeleteBat")
    public String prodParamDeleteBat(String[] param, HttpServletResponse response) throws IOException {
        //response.setContentType("text/html;charset=utf-8");
        //PrintWriter out=response.getWriter();
        productService.deleteProductParameter(param);
        System.out.println(param);
        return "redirect:productList";
    }

}
