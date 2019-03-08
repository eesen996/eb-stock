package com.yun.report.dao;

import com.yun.report.model.ProductSaleCalc;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ProductSaleDao {

    @Results(id = "productSaleCalc",value = {
            @Result(property = "label",column = "trade_date"),
            @Result(property = "saleNum",column = "sale_num")
    })
    @Select("<script>" +
            "select DATE_FORMAT(pt.trade_date,'%Y-%m-%d') trade_date,sum(ifnull(pt.trade_del,0)) sale_num" +
            " from product_trade pt,product p,product_parameter pp" +
            " where pt.prod_id=p.prod_id and pt.prod_param_id=pp.prod_param_id and " +
            " pt.trade_date BETWEEN #{startDate} and #{endDate} " +
            "<if test='param.prodName!=null'> and p.prod_name like #{param.prodName}</if>" +
            "<if test='param.prodParamName!=null'> and pp.prod_param_name like #{param.prodParamName}</if>" +
            " group by DATE_FORMAT(pt.trade_date,'%Y-%m-%d') " +
            "</script>")
    List<ProductSaleCalc> findProductSaleBarChart(@Param("startDate") String startDate,
                                                  @Param("endDate") String endDate,
                                                  @Param("param") Map<String,Object> param);
}
