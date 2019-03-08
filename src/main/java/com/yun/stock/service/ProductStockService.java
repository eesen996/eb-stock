package com.yun.stock.service;

import com.yun.common.Page;
import com.yun.exception.EbStockException;
import com.yun.stock.dao.ProductReceiveStockDao;
import com.yun.stock.dao.ProductReceiveTradeDao;
import com.yun.stock.dao.ProductStockDao;
import com.yun.stock.dao.ProductTradeDao;
import com.yun.stock.model.ProductReceiveStock;
import com.yun.stock.model.ProductReceiveTrade;
import com.yun.stock.model.ProductStock;
import com.yun.stock.model.ProductTrade;
import com.yun.user.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Transactional
@Service
public class ProductStockService {

    //现有物品库存
    @Autowired
    private ProductStockDao productStockDao;

    @Autowired
    private ProductReceiveTradeDao productReceiveTradeDao;

    @Autowired
    private ProductTradeDao productTradeDao;

    public List<ProductStock> findProductStockList(Map<String,Object> param,Page page) {
        return productStockDao.findProductStockList(param,page);
    }

    public int findProductStockRowCnt(Map<String,Object> param) {
        return productStockDao.findProductStockRowCnt(param);
    }

    public void insertProductStock(ProductStock productStock) {
        productStockDao.insertProductStock(productStock);
    }

    public void deleteProductStock(int prodStockId) {
        productStockDao.deleteProductStock(prodStockId);
    }

    public ProductStock findProductStockById(int prodStockId) {
        return productStockDao.findProductStockById(prodStockId);
    }

    public void updateProductStock(ProductStock productStock) {
        productStockDao.updateProductStock(productStock);
    }


    //------待收库存
    @Autowired
    private ProductReceiveStockDao productReceiveStockDao;

    public List<ProductReceiveStock> findProductReceiveStockList(Map<String,Object> param, Page page) {
        return productReceiveStockDao.findProductReceiveStockList(param,page);
    }

    public int findProductReceiveStockRowCnt(Map<String,Object> param) {
        return productReceiveStockDao.findProductReceiveStockRowCnt(param);
    }

    public void insertProductReceiveStock(ProductReceiveStock productReceiveStock) {
        productReceiveStockDao.insertProductReceiveStock(productReceiveStock);
    }

    public void deleteProductReceiveStock(int prodReceStockId) {
        productReceiveStockDao.deleteProductReceiveStock(prodReceStockId);
    }

    public ProductReceiveStock findProductReceiveStockById(int prodReceStockId) {
        return productReceiveStockDao.findProductReceiveStockById(prodReceStockId);
    }

    public void updateProductReceiveStock(ProductReceiveStock productReceiveStock) {
        productReceiveStockDao.updateProductReceiveStock(productReceiveStock);
    }

    public int findProductReceiveStockNumByProdIdAndProdParamId(Integer prodId, Integer prodParamId) {
       return productReceiveStockDao.findProductReceiveStockNumByProdIdAndProdParamId(prodId,prodParamId);
    }

    public List<ProductReceiveStock> findProductReceiveStockRecordByProdIdAndProdParamId(Integer prodId, Integer prodParamId){
       return productReceiveStockDao.findProductReceiveStockRecordByProdIdAndProdParamId(prodId,prodParamId);
    }

    //-----转库

    public String updateReceiveToCurrentStock(Date tradeDate, int prodReceStockId, int tradeNum,UserInfo userInfo) throws EbStockException {
        try {
            ProductReceiveStock productReceiveStock = productReceiveStockDao.findProductReceiveStockById(prodReceStockId);

            //保存待收库存减少交易记录
            ProductReceiveTrade productReceiveTrade = new ProductReceiveTrade();
            productReceiveTrade.setProduct(productReceiveStock.getProduct());
            productReceiveTrade.setProductParameter(productReceiveStock.getProductParameter());
            productReceiveTrade.setBeginNum(productReceiveStock.getStockNum());
            productReceiveTrade.setTradeDel(tradeNum);
            productReceiveTrade.setEndNum(productReceiveStock.getStockNum() - tradeNum);
            productReceiveTrade.setTradeDate(tradeDate);
            productReceiveTrade.setOper(userInfo);
            productReceiveTradeDao.insertProductReceiveOutTrade(productReceiveTrade);

            //保存现有库存增加记录
            ProductTrade productTrade = new ProductTrade();
            int prodId = productReceiveStock.getProduct().getProdId();
            int prodParamId = productReceiveStock.getProductParameter().getProdParamId();
            List<ProductStock> productStockList = productStockDao.findProductStockByProdIdAndProdParamId(prodId, prodParamId);
            int beginNum=0;
            if (productStockList.size() == 0) {
                ProductStock productStock = new ProductStock();
                productStock.setProduct(productReceiveStock.getProduct());
                productStock.setProductParameter(productReceiveStock.getProductParameter());
                productStock.setStockNum(tradeNum);
                productStockDao.insertProductStock(productStock);
            } else if (productStockList.size() == 1) {
                ProductStock productStock = productStockList.get(0);
                beginNum=productStock.getStockNum();
                productStock.setStockNum(beginNum + tradeNum);
                productStockDao.updateProductStock(productStock);
            } else {
                throw new EbStockException("库存数据有错误：" + prodId + "-prodParamId:" + prodParamId + "不唯一");
            }

            productTrade.setTradeDate(tradeDate);
            productTrade.setProduct(productReceiveStock.getProduct());
            productTrade.setProductParameter(productReceiveStock.getProductParameter());
            productTrade.setBeginNum(beginNum);
            productTrade.setTradeAdd(tradeNum);
            productTrade.setEndNum(beginNum+tradeNum);
            productTrade.setOper(userInfo);
            productTradeDao.insertProductInTrade(productTrade);

            //修改待收库存数
            productReceiveStock.setStockNum(productReceiveStock.getStockNum() - tradeNum);
            productReceiveStockDao.updateProductReceiveStock(productReceiveStock);
            return "待收库存转现有库存操作完成";
        }catch (EbStockException e){
            log.debug(e+"");
            throw new EbStockException(e.getMessage());
        }
    }

    public List<ProductStock> findProductStockRecordByProdIdAndProdParamId(int prodId, int prodParamId) {
        return productStockDao.findProductStockByProdIdAndProdParamId(prodId,prodParamId);
    }
}
