package com.yun.stock.dao;

import com.yun.common.Page;
import com.yun.stock.model.ProductStock;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ProductStockDao {

    @Results(id = "productStock",value = {
            @Result(property = "prodStockId",column = "prod_stock_id"),
            @Result(property = "stockNum",column = "stock_num"),
            @Result(property = "product.prodId",column = "prod_id"),
            @Result(property = "product.prodName",column = "prod_name"),
            @Result(property = "product.prodNo",column = "prod_no"),
            @Result(property = "product.prodState",column = "prod_state"),
            @Result(property = "productParameter.prodParamId",column = "prod_param_id"),
            @Result(property = "productParameter.prodParamName",column = "prod_param_name")
    })
    @Select("<script>select ps.prod_stock_id,ps.stock_num,p.prod_id,p.prod_name,p.prod_no,prod_state,pp.prod_param_id,pp.prod_param_name" +
            " from product_stock ps inner join product p on ps.prod_id=p.prod_id " +
            " left join product_parameter pp on ps.prod_param_id=pp.prod_param_id where 1=1" +
            " <if test='param.prodName!=null'> and p.prod_name like #{param.prodName}</if>" +
            " order by p.prod_id,pp.prod_param_id limit #{page.startNum},#{page.pageSize}" +
            "</script>")
    List<ProductStock> findProductStockList(@Param("param") Map<String,Object> param,@Param("page") Page page);

    @Select("<script>select count(*)" +
            " from product_stock ps inner join product p on ps.prod_id=p.prod_id " +
            " left join product_parameter pp on ps.prod_param_id=pp.prod_param_id where 1=1" +
            " <if test='param.prodName!=null'> and p.prod_name like #{param.prodName}</if>" +
            "</script>")
    int findProductStockRowCnt(@Param("param") Map<String,Object> param);

    @Insert("insert into product_stock(prod_id,prod_param_id,stock_num)" +
            "values(#{product.prodId},#{productParameter.prodParamId},#{stockNum})")
    void insertProductStock(ProductStock productStock);

    @Delete("delete from product_stock where prod_stock_id=#{prodStockId}")
    void deleteProductStock(int prodStockId);

    @Select("<script>select ps.prod_stock_id,ps.stock_num,p.prod_id,p.prod_name,p.prod_no,prod_state,pp.prod_param_id,pp.prod_param_name" +
            " from product_stock ps inner join product p on ps.prod_id=p.prod_id " +
            " left join product_parameter pp on ps.prod_param_id=pp.prod_param_id where 1=1 and ps.prod_stock_id=#{prodStockId}" +
            "</script>")
    @ResultMap("productStock")
    ProductStock findProductStockById(int prodStockId);

    @Update("update product_stock set prod_id=#{product.prodId}," +
            "prod_param_id=#{productParameter.prodParamId}," +
            "stock_num=#{stockNum} where prod_stock_id=#{prodStockId}")
    void updateProductStock(ProductStock productStock);

    @Select("select ps.prod_stock_id,ps.stock_num,p.prod_id,p.prod_name,p.prod_no,prod_state," +
            "pp.prod_param_id,pp.prod_param_name "+
            " from product_stock ps inner join product p on ps.prod_id=p.prod_id " +
            " left join product_parameter pp on ps.prod_param_id=pp.prod_param_id where 1=1" +
            " and p.prod_id=#{prodId} and pp.prod_param_Id=#{prodParamId}")
    @ResultMap("productStock")
    List<ProductStock> findProductStockByProdIdAndProdParamId(@Param("prodId") int prodId,@Param("prodParamId") int prodParamId);

    @Select("select stock_num from product_stock " +
            "where 1=1 and prod_id=#{prodId} and prod_param_id=#{prodParamId}")
    int findProductStockNumByProdIdAndProdParamId(@Param("prodId")int prodId,@Param("prodParamId") int prodParamId);
}
