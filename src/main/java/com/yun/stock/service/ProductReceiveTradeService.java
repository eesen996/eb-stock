package com.yun.stock.service;

import com.yun.exception.EbStockException;
import com.yun.stock.dao.ProductReceiveStockDao;
import com.yun.stock.dao.ProductReceiveTradeDao;
import com.yun.stock.model.ProductReceiveStock;
import com.yun.stock.model.ProductReceiveTrade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ProductReceiveTradeService {

    @Autowired
    private ProductReceiveTradeDao productReceiveTradeDao;

    @Autowired
    private ProductReceiveStockDao productReceiveStockDao;

    public String insertProductReceiveInTrade(ProductReceiveTrade productReceiveTrade) throws EbStockException{
        try {
            //1.先计算待收中某个物品的库存数量
            int prodId=productReceiveTrade.getProduct().getProdId();
            int prodParamId=productReceiveTrade.getProductParameter().getProdParamId();
            int beginNum=0;
            List<ProductReceiveStock> productReceiveStockList=productReceiveStockDao.findProductReceiveStockRecordByProdIdAndProdParamId(prodId,prodParamId);
            int size=productReceiveStockList.size();
            if(size==0){
                //如果没有查询到记录，待收库存表登记一条该物品和参数对应的库存数量
                beginNum=0;
                ProductReceiveStock productReceiveStock=new ProductReceiveStock();
                productReceiveStock.setProduct(productReceiveTrade.getProduct());
                productReceiveStock.setProductParameter(productReceiveTrade.getProductParameter());
                productReceiveStock.setStockNum(beginNum+productReceiveTrade.getTradeAdd());
                productReceiveStockDao.insertProductReceiveStock(productReceiveStock);
            }else if(size==1){
                //如果找到只有一条待收库存表记录，那些修改库存
                beginNum=productReceiveStockDao.findProductReceiveStockNumByProdIdAndProdParamId(prodId,prodParamId);
                ProductReceiveStock productReceiveStock=productReceiveStockList.get(0);
                productReceiveStock.setStockNum(beginNum+productReceiveTrade.getTradeAdd());
                productReceiveStockDao.updateProductReceiveStock(productReceiveStock);
            }else{
                //如果size>1说明数据有错误，终止操作
                throw new EbStockException("待收库存数据有错误prodId:"+prodId+"--prodParamId:"+prodParamId+"有重复");
            }
            productReceiveTrade.setBeginNum(beginNum);
            productReceiveTrade.setEndNum(beginNum+productReceiveTrade.getTradeAdd());
            //2.登记交易记录
            productReceiveTradeDao.insertProductReceiveInTrade(productReceiveTrade);
            String result="待收库存入库登记"+productReceiveTrade.getTradeAdd()+"个物品完成，目前库存数量"+(beginNum+productReceiveTrade.getTradeAdd())+"个";
            return result;
        }catch (Exception e){
            log.debug(e+"");
            throw new EbStockException(e.getMessage());
        }
    }

    public String insertProductReceiveRejectTrade(ProductReceiveTrade productReceiveTrade) throws EbStockException {
        try {
            //1.先计算待收中某个物品的库存数量
            int prodId=productReceiveTrade.getProduct().getProdId();
            int prodParamId=productReceiveTrade.getProductParameter().getProdParamId();
            int beginNum=0;
            List<ProductReceiveStock> productReceiveStockList=productReceiveStockDao.findProductReceiveStockRecordByProdIdAndProdParamId(prodId,prodParamId);
            int size=productReceiveStockList.size();
            if(size==0){
                throw new EbStockException("输入的数据有误，该物品没有库存无法执行退货操作");
            }else if(size==1){
                //如果找到只有一条待收库存表记录，那些修改库存
                beginNum=productReceiveStockDao.findProductReceiveStockNumByProdIdAndProdParamId(prodId,prodParamId);
                ProductReceiveStock productReceiveStock=productReceiveStockList.get(0);
                productReceiveStock.setStockNum(beginNum-productReceiveTrade.getTradeDel());
                productReceiveStockDao.updateProductReceiveStock(productReceiveStock);
            }else{
                //如果size>1说明数据有错误，终止操作
                throw new EbStockException("待收库存数据有错误prodId:"+prodId+"--prodParamId:"+prodParamId+"出现重复");
            }
            productReceiveTrade.setBeginNum(beginNum);
            productReceiveTrade.setEndNum(beginNum-productReceiveTrade.getTradeDel());
            //2.登记交易记录
            productReceiveTradeDao.insertProductReceiveOutTrade(productReceiveTrade);
            String result="待收库存退货登记"+productReceiveTrade.getTradeDel()+"个物品完成，目前库存数量"+(beginNum-productReceiveTrade.getTradeDel())+"个";
            return result;
        }catch (EbStockException e){
            log.debug(e+"");
            throw new EbStockException(e.getMessage());
        }
    }
}
