package com.yun.report.service;

import com.yun.common.ColorHelper;
import com.yun.report.dao.ProductReceCurrDao;
import com.yun.report.dao.ProductSaleDao;
import com.yun.report.model.chart.ChartBar;
import com.yun.report.model.ProductReceCurr;
import com.yun.report.model.ProductSale;
import com.yun.report.model.ProductSaleCalc;
import com.yun.report.model.chart.DataSets;
import com.yun.report.model.chart.DataValue;
import com.yun.stock.model.ProductReceiveTrade;
import com.yun.stock.model.ProductTrade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ProductStockReportService {

    @Autowired
    private ProductReceCurrDao productReceCurrDao;

    public List<ProductReceCurr> findProductReceCurrList(Map<String,Object> param){
        return productReceCurrDao.findProductReceCurrList(param);
    }

    public List<ProductReceiveTrade> findProductReceiveStockChange(String startDate, String endDate,Map<String,Object> param) {
        return productReceCurrDao.findProductReceiveStockChange(startDate,endDate,param);
    }

    public List<ProductTrade> findProductStockChange(String startDate, String endDate,Map<String,Object> param) {
        return productReceCurrDao.findProductStockChange(startDate,endDate,param);
    }

    public List<ProductSale> findProductSaleTotal(String startDate, String endDate, Map<String,Object> param) {
        return productReceCurrDao.findProductSaleTotal(startDate,endDate,param);
    }

    @Autowired
    private ProductSaleDao productSaleDao;
    public List<ProductSaleCalc> findProductSaleCalcList(String startDate, String endDate, Map<String,Object> param) {
        List<ProductSaleCalc> productSaleCalcList=productSaleDao.findProductSaleBarChart(startDate,endDate,param);
        return productSaleCalcList;
    }
}
