package com.yun.report.dao;

import com.yun.report.model.ProductReceCurr;
import com.yun.report.model.ProductSale;
import com.yun.stock.model.ProductReceiveTrade;
import com.yun.stock.model.ProductTrade;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ProductReceCurrDao {


    @Results(id = "prodReceCurr",value = {
            @Result(property = "prodId",column = "prod_id"),
            @Result(property = "prodName",column = "prod_name"),
            @Result(property = "prodParamId",column = "prod_param_id"),
            @Result(property = "prodParamName",column = "prod_param_name"),
            @Result(property = "prodReceNum",column = "prod_rece_num"),
            @Result(property = "prodCurrNum",column = "prod_curr_num"),
    })
    @Select("<script>select ps.*,ps.prod_rece_num+ps.prod_curr_num stockNum from(" +
            " select t.prod_id,t.prod_name,t.prod_param_id,t.prod_param_name," +
            " ifnull((select stock_num from product_receive_stock prs where prs.prod_id=t.prod_id and prs.prod_param_id=t.prod_param_id),0) prod_rece_num," +
            " ifnull((select stock_num from product_stock ps where ps.prod_id=t.prod_id and ps.prod_param_id=t.prod_param_id),0) prod_curr_num" +
            " from (select p.prod_id,p.prod_name,pp.prod_param_id,pp.prod_param_name" +
            " from product p LEFT JOIN product_parameter pp on p.prod_id=pp.prod_id where p.prod_state=0 and pp.param_state=0 order by p.prod_id,pp.prod_param_id) t)ps" +
            " where 1=1" +
            "<if test='prodName!=null'> and ps.prod_name like #{prodName}</if>" +
            "<if test='prodParamName!=null'> and ps.prod_param_name like #{prodParamName}</if>" +
            "<if test='stockNum!=null'> and (ps.prod_rece_num+ps.prod_curr_num)&lt; #{stockNum}</if></script>")
    List<ProductReceCurr> findProductReceCurrList(Map<String,Object> param);

    @Results(id = "prodReceTradeReport",value = {
            @Result(property = "prodReceTradeId",column ="prod_rece_trade_id" ),
            @Result(property = "tradeDate",column ="trade_date" ),
            @Result(property = "product.prodId",column ="prod_id" ),
            @Result(property = "product.prodName",column ="prod_name" ),
            @Result(property = "productParameter.prodParamId",column ="prod_param_id" ),
            @Result(property = "productParameter.prodParamName",column ="prod_param_name" ),
            @Result(property = "beginNum",column ="begin_num" ),
            @Result(property = "tradeAdd",column ="trade_add" ),
            @Result(property = "tradeDel",column ="trade_del" ),
            @Result(property = "endNum",column ="end_num" ),
            @Result(property = "oper.userId",column ="oper_id" ),
            @Result(property = "oper.userName",column ="user_name" ),
    })
    @Select("<script>select t.prod_rece_trade_id, t.trade_date,t.prod_id,p.prod_name,t.prod_param_id,pp.prod_param_name," +
            " t.begin_num,t.trade_add,t.trade_del,t.end_num,t.oper_id,u.user_name" +
            " from product_receive_trade t,product p,product_parameter pp,user_info u" +
            " where t.prod_id=p.prod_id and t.prod_param_id=pp.prod_param_id and t.oper_id=u.user_id" +
            " and t.trade_date BETWEEN #{startDate} and #{endDate}" +
            "<if test='param.prodName!=null'> and prod_name like #{param.prodName}</if>" +
            "<if test='param.prodParamName!=null'> and prod_param_name like #{param.prodParamName}</if>" +
            " ORDER BY t.prod_id,t.prod_param_id,t.trade_date</script>")
    List<ProductReceiveTrade> findProductReceiveStockChange(@Param("startDate") String startDate,
                                                            @Param("endDate") String endDate,
                                                            @Param("param") Map<String,Object> param);


    @Results(id = "prodCurrTradeReport",value = {
            @Result(property = "prodTradeId",column ="prod_trade_id" ),
            @Result(property = "tradeDate",column ="trade_date" ),
            @Result(property = "product.prodId",column ="prod_id" ),
            @Result(property = "product.prodName",column ="prod_name" ),
            @Result(property = "productParameter.prodParamId",column ="prod_param_id" ),
            @Result(property = "productParameter.prodParamName",column ="prod_param_name" ),
            @Result(property = "beginNum",column ="begin_num" ),
            @Result(property = "tradeAdd",column ="trade_add" ),
            @Result(property = "tradeDel",column ="trade_del" ),
            @Result(property = "endNum",column ="end_num" ),
            @Result(property = "oper.userId",column ="oper_id" ),
            @Result(property = "oper.userName",column ="user_name" ),
    })
    @Select("<script>select t.prod_trade_id, t.trade_date,t.prod_id,p.prod_name,t.prod_param_id,pp.prod_param_name," +
            " t.begin_num,t.trade_add,t.trade_del,t.end_num,t.oper_id,u.user_name" +
            " from product_trade t,product p,product_parameter pp,user_info u" +
            " where t.prod_id=p.prod_id and t.prod_param_id=pp.prod_param_id and t.oper_id=u.user_id" +
            " and t.trade_date BETWEEN #{startDate} and #{endDate}" +
            "<if test='param.prodName!=null'> and prod_name like #{param.prodName}</if>" +
            "<if test='param.prodParamName!=null'> and prod_param_name like #{param.prodParamName}</if>" +
            " ORDER BY t.prod_id,t.prod_param_id,t.trade_date</script>")
    List<ProductTrade> findProductStockChange(@Param("startDate") String startDate,
                                              @Param("endDate") String endDate,
                                              @Param("param") Map<String,Object> param);

    @Results(id = "prodSale",value = {
            @Result(property = "prodId",column ="prod_id" ),
            @Result(property = "prodName",column ="prod_name" ),
            @Result(property = "prodParamId",column ="prod_param_id" ),
            @Result(property = "prodParamName",column ="prod_param_name" ),
            @Result(property = "saleNum",column ="saleNum" )
    })
    @Select("<script>select p.prod_id,p.prod_name,pp.prod_param_id,pp.prod_param_name,sum(pt.trade_del) saleNum" +
            " from product p,product_parameter pp,product_trade pt where p.prod_id=pt.prod_id and pp.prod_param_id=pt.prod_param_id" +
            " and pt.trade_date BETWEEN #{startDate} and #{endDate}" +
            "<if test='param.prodName!=null'> and p.prod_name like #{param.prodName}</if>" +
            "<if test='param.prodParamName!=null'> and pp.prod_param_name like #{param.prodParamName}</if>" +
            " group by  p.prod_id,p.prod_name,pp.prod_param_id,pp.prod_param_name" +
            " ORDER BY saleNum desc</script>")
    List<ProductSale> findProductSaleTotal(@Param("startDate")String startDate,
                                           @Param("endDate")String endDate,
                                           @Param("param")Map<String,Object> param);
}
