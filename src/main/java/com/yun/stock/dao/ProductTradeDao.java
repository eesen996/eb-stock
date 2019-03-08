package com.yun.stock.dao;

import com.yun.stock.model.ProductTrade;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProductTradeDao {

    @Insert("insert into product_trade(trade_date,prod_id," +
            "prod_param_id,begin_num,trade_add,end_num,oper_id)" +
            "values(#{tradeDate},#{product.prodId},#{productParameter.prodParamId}," +
            "#{beginNum},#{tradeAdd},#{endNum},#{oper.userId})")
    void insertProductInTrade(ProductTrade productTrade);

    @Insert("insert into product_trade(trade_date,prod_id," +
            "prod_param_id,begin_num,trade_del,end_num,oper_id)" +
            "values(#{tradeDate},#{product.prodId},#{productParameter.prodParamId}," +
            "#{beginNum},#{tradeDel},#{endNum},#{oper.userId})")
    void insertProductOutTrade(ProductTrade productTrade);
}
