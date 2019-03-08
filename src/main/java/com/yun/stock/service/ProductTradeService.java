package com.yun.stock.service;

import com.yun.exception.EbStockException;
import com.yun.stock.dao.ProductReceiveStockDao;
import com.yun.stock.dao.ProductReceiveTradeDao;
import com.yun.stock.dao.ProductStockDao;
import com.yun.stock.dao.ProductTradeDao;
import com.yun.stock.model.ProductReceiveStock;
import com.yun.stock.model.ProductReceiveTrade;
import com.yun.stock.model.ProductStock;
import com.yun.stock.model.ProductTrade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ProductTradeService {

    @Autowired
    private ProductTradeDao productTradeDao;

    @Autowired
    private ProductStockDao productStockDao;

    public String insertProductInTrade(ProductTrade productTrade) throws EbStockException{
        try {
            //1.先计算待收中某个物品的库存数量
            int prodId=productTrade.getProduct().getProdId();
            int prodParamId=productTrade.getProductParameter().getProdParamId();
            int beginNum=0;
            List<ProductStock> productStockList=productStockDao.findProductStockByProdIdAndProdParamId(prodId,prodParamId);
            int size=productStockList.size();
            if(size==0){
                //如果没有查询到记录，库存表登记一条该物品和参数对应的库存数量
                beginNum=0;
                ProductStock productStock=new ProductStock();
                productStock.setProduct(productTrade.getProduct());
                productStock.setProductParameter(productTrade.getProductParameter());
                productStock.setStockNum(beginNum+productTrade.getTradeAdd());
                productStockDao.insertProductStock(productStock);
            }else if(size==1){
                //如果找到只有一条待收库存表记录，那些修改库存
                beginNum=productStockDao.findProductStockNumByProdIdAndProdParamId(prodId,prodParamId);
                ProductStock productStock=productStockList.get(0);
                productStock.setStockNum(beginNum+productTrade.getTradeAdd());
                productStockDao.updateProductStock(productStock);
            }else{
                //如果size>1说明数据有错误，终止操作
                throw new EbStockException("现有库存数据有错误prodId:"+prodId+"--prodParamId:"+prodParamId+"记录有重复");
            }
            productTrade.setBeginNum(beginNum);
            productTrade.setEndNum(beginNum+productTrade.getTradeAdd());
            //2.登记交易记录
            productTradeDao.insertProductInTrade(productTrade);
            String result="现有库存入库登记"+productTrade.getTradeAdd()+"个物品完成，目前库存数量"+(beginNum+productTrade.getTradeAdd())+"个";
            return result;
        }catch (Exception e){
            log.debug(e+"");
            throw new EbStockException(e.getMessage());
        }
    }

    public String insertProductOutTrade(ProductTrade productTrade) throws EbStockException {
        try {
            //1.先计算待收中某个物品的库存数量
            int prodId=productTrade.getProduct().getProdId();
            int prodParamId=productTrade.getProductParameter().getProdParamId();
            int beginNum=0;
            List<ProductStock> productStockList=productStockDao.findProductStockByProdIdAndProdParamId(prodId,prodParamId);
            int size=productStockList.size();
            if(size==0){
                throw new EbStockException("输入的数据有误，该物品没有库存无法执行出库操作");
            }else if(size==1){
                //如果找到只有一条待收库存表记录，那些修改库存
                beginNum=productStockDao.findProductStockNumByProdIdAndProdParamId(prodId,prodParamId);
                ProductStock productStock=productStockList.get(0);
                productStock.setStockNum(beginNum-productTrade.getTradeDel());
                productStockDao.updateProductStock(productStock);
            }else{
                //如果size>1说明数据有错误，终止操作
                throw new EbStockException("现有库存数据有错误prodId:"+prodId+"--prodParamId:"+prodParamId+"出现重复");
            }
            productTrade.setBeginNum(beginNum);
            productTrade.setEndNum(beginNum-productTrade.getTradeDel());
            //2.登记交易记录
            productTradeDao.insertProductOutTrade(productTrade);
            String result="现有库存出库登记"+productTrade.getTradeDel()+"个物品完成，目前库存数量"+(beginNum-productTrade.getTradeDel())+"个";
            return result;
        }catch (EbStockException e){
            log.debug(e+"");
            throw new EbStockException(e.getMessage());
        }
    }


}
