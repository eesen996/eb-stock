package com.yun.stock.dao;

import com.yun.stock.model.ProductReceiveTrade;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProductReceiveTradeDao {

    @Insert("insert into product_receive_trade(trade_date,prod_id," +
            "prod_param_id,begin_num,trade_add,end_num,oper_id)" +
            "values(#{tradeDate},#{product.prodId},#{productParameter.prodParamId}," +
            "#{beginNum},#{tradeAdd},#{endNum},#{oper.userId})")
    void insertProductReceiveInTrade(ProductReceiveTrade productReceiveTrade);

    @Insert("insert into product_receive_trade(trade_date,prod_id," +
            "prod_param_id,begin_num,trade_del,end_num,oper_id)" +
            "values(#{tradeDate},#{product.prodId},#{productParameter.prodParamId}," +
            "#{beginNum},#{tradeDel},#{endNum},#{oper.userId})")
    void insertProductReceiveOutTrade(ProductReceiveTrade productReceiveTrade);
}
