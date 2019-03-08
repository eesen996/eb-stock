package com.yun.report.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yun.common.ColorHelper;
import com.yun.common.DateHelper;
import com.yun.common.JsonHelper;
import com.yun.report.model.ProductSaleCalc;
import com.yun.report.model.chart.ChartBar;
import com.yun.report.model.ProductReceCurr;
import com.yun.report.model.ProductSale;
import com.yun.report.model.chart.DataSets;
import com.yun.report.model.chart.DataValue;
import com.yun.report.service.ProductStockReportService;
import com.yun.stock.model.ProductReceiveTrade;
import com.yun.stock.model.ProductTrade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class ProductStockReportController {

    @Autowired
    private ProductStockReportService productStockReportService;

    /**
     * 物品库存报表
     */
    @RequestMapping("/productStockReport")
    public String productStockReport(String prodName,String prodParamName,String stockNum,Model model){
        Map<String,Object> param=new HashMap<>();
        if(null!=prodName&&!"".equals(prodName)){
            param.put("prodName","%"+prodName+"%");
        }
        if(null!=prodParamName&&!"".equals(prodParamName)){
            param.put("prodParamName","%"+prodParamName+"%");
        }
        if(null!=stockNum&&!"".equals(stockNum)){
            param.put("stockNum",stockNum);
        }

        List<ProductReceCurr> productReceCurrList=productStockReportService.findProductReceCurrList(param);
        model.addAttribute("prodName",prodName);
        model.addAttribute("prodParamName",prodParamName);
        model.addAttribute("stockNum",stockNum);
        model.addAttribute("productReceCurrList",productReceCurrList);
        return "report/productStockReport";
    }

    /**
     * 待收库存变动
     */
    @RequestMapping("/productReceiveStockChange")
    public String productReceiveStockChange(String startDate, String endDate,String prodName,String prodParamName,Model model){
        Map<String,Object> param=new HashMap<>();
        if(null!=prodName&&!"".equals(prodName)){
            param.put("prodName","%"+prodName+"%");
        }
        if(null!=prodParamName&&!"".equals(prodParamName)){
            param.put("prodParamName","%"+prodParamName+"%");
        }
        if(null==startDate||"".equals(startDate)){
            startDate=DateHelper.getCurrentDate("yyyy-MM-dd");
        }
        if(null==endDate||"".equals(endDate)){
            endDate=DateHelper.getCurrentDate("yyyy-MM-dd");
        }
        List<ProductReceiveTrade> productReceiveTradeList=productStockReportService.findProductReceiveStockChange(startDate,endDate,param);
        model.addAttribute("startDate",startDate);
        model.addAttribute("endDate",endDate);
        model.addAttribute("prodName",prodName);
        model.addAttribute("prodParamName",prodParamName);
        model.addAttribute("productReceiveTradeList",productReceiveTradeList);
        return "report/productReceiveChange";
    }

    /**
     * 现有库存变动
     */
    @RequestMapping("/productCurrentStockChange")
    public String productCurrentStockChange(String startDate, String endDate,String prodName,String prodParamName, Model model){
        Map<String,Object> param=new HashMap<>();
        if(null!=prodName&&!"".equals(prodName)){
            param.put("prodName","%"+prodName+"%");
        }
        if(null!=prodParamName&&!"".equals(prodParamName)){
            param.put("prodParamName","%"+prodParamName+"%");
        }
        if(null==startDate||"".equals(startDate)){
            startDate=DateHelper.getCurrentDate("yyyy-MM-dd");
        }
        if(null==endDate||"".equals(endDate)){
            endDate=DateHelper.getCurrentDate("yyyy-MM-dd");
        }
        List<ProductTrade> productTradeList=productStockReportService.findProductStockChange(startDate,endDate,param);
        model.addAttribute("startDate",startDate);
        model.addAttribute("endDate",endDate);
        model.addAttribute("prodName",prodName);
        model.addAttribute("prodParamName",prodParamName);
        model.addAttribute("productTradeList",productTradeList);
        return "report/productCurrentChange";
    }

    /**
     * 销售排名
     */
    @RequestMapping("/productSaleTop")
    public String productSaleTop(String startDate,String endDate,String prodName,String prodParamName,Model model){
        Map<String,Object> param=new HashMap<>();
        if(null!=prodName&&!"".equals(prodName)){
            param.put("prodName","%"+prodName+"%");
        }
        if(null!=prodParamName&&!"".equals(prodParamName)){
            param.put("prodParamName","%"+prodParamName+"%");
        }
        if(null==startDate||"".equals(startDate)){
            startDate=DateHelper.getCurrentDate("yyyy-MM-dd");
        }
        if(null==endDate||"".equals(endDate)){
            endDate=DateHelper.getCurrentDate("yyyy-MM-dd");
        }

        List<ProductSale> productSaleList=productStockReportService.findProductSaleTotal(startDate,endDate,param);
        model.addAttribute("startDate",startDate);
        model.addAttribute("endDate",endDate);
        model.addAttribute("prodName",prodName);
        model.addAttribute("prodParamName",prodParamName);
        model.addAttribute("productSaleList",productSaleList);
        return "report/productSaleTop";
    }

    /**
     * 销售统计柱状图,查询页面
     */
    @RequestMapping("/productSaleCalc")
    public String productSaleCalc(){
        return "report/productSaleCalc";
    }

    /**
     * 销售统计柱状图,查询ajax调用
     */
    @RequestMapping("/productSaleCalcQuery")
    @ResponseBody
    public String productSaleCalcQuery(String startDate,String endDate,String prodName,String prodParamName){
        String json="";
        try {
            Map<String,Object> param=new HashMap<>();
            if(null!=prodName&&!"".equals(prodName)){
                param.put("prodName","%"+prodName+"%");
            }
            if(null!=prodParamName&&!"".equals(prodParamName)){
                param.put("prodParamName","%"+prodParamName+"%");
            }
            if(null==startDate||"".equals(startDate)){
                startDate=DateHelper.getCurrentDate("yyyy-MM-dd");
            }
            if(null==endDate||"".equals(endDate)){
                endDate=DateHelper.getCurrentDate("yyyy-MM-dd");
            }

            List<ProductSaleCalc> productSaleCalcList=productStockReportService.findProductSaleCalcList(startDate,endDate,param);
            ChartBar chartData=listDate2CharBar(productSaleCalcList);
            Map<String,Object> data=new HashMap();
            data.put("list",productSaleCalcList);
            data.put("chart",chartData);
            json=JsonHelper.getJsonString(0,"查询物品销售数据成功",data);
        } catch (JsonProcessingException e) {
            log.error("查询物品销售数据出现错误"+e);
            json=JsonHelper.getJsonString(-1,"查询物品销售数据出现错误");
        }
        return json;
    }

    private ChartBar listDate2CharBar(List<ProductSaleCalc> productSaleCalcList){
        ChartBar chartBar=new ChartBar();
        DataSets dataSets=new DataSets();
        chartBar.setData(dataSets);
        //柱状图下标
        String[] labels=new String[productSaleCalcList.size()];
        for(int i=0;i<productSaleCalcList.size();i++){
            ProductSaleCalc productSaleCalc=productSaleCalcList.get(i);
            labels[i]=productSaleCalc.getLabel();
        }
        dataSets.setLabels(labels);

        //默认数据只有一组

        DataValue dv=new DataValue();
        dv.setLabel("销售数量");
        int[] datas=new int[productSaleCalcList.size()];
        String[] backgroundColors=new String[productSaleCalcList.size()];
        String[] borderColors=new String[productSaleCalcList.size()];
        for(int i=0;i<productSaleCalcList.size();i++){
            ProductSaleCalc productSaleCalc=productSaleCalcList.get(i);
            datas[i]=productSaleCalc.getSaleNum();
            backgroundColors[i]=ColorHelper.getRBGColor(0.2);
            borderColors[i]=ColorHelper.getRBGColor(1);
        }
        dv.setData(datas);
        dv.setBackgroundColor(backgroundColors);
        dv.setBorderColor(borderColors);
        DataValue[] dataValues=new DataValue[]{dv};
        dataSets.setDatasets(dataValues);

        return chartBar;
    }
}
