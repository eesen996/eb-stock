package com.yun.product.dao;

import com.yun.common.Page;
import com.yun.product.model.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ProductDao {

    @Results(id = "product",value = {
            @Result(property = "prodId",column = "prod_id"),
            @Result(property = "prodName",column = "prod_name"),
            @Result(property = "prodNo",column = "prod_No"),
            @Result(property = "productType.prodTypeId",column = "prod_type_id"),
            @Result(property = "productType.prodTypeName",column = "prod_type_name"),
            @Result(property = "prodState",column = "prod_state")
    })
    @Select("<script>select * from product p left join product_type pt on p.prod_type_id=pt.prod_type_id where 1=1 and p.prod_state=0" +
            "<if test='param.prodName!=null'> and p.prod_name like #{param.prodName}</if>" +
            "<if test='param.prodNo!=null'> and p.prod_no like #{param.prodNo}</if>" +
            "<if test='param.prodTypeName!=null'> and pt.prod_type_name like #{param.prodTypeName}</if>" +
            " order by prod_id" +
            " <if test='page!=null'>limit #{page.startNum},#{page.pageSize}</if></script>")
    List<Product> findProductByParam(@Param("param") Map param, @Param("page") Page page);

    @Select("<script>select count(*) from product p left join product_type pt on p.prod_type_id=pt.prod_type_id where 1=1 and p.prod_state=0" +
            "<if test='param.prodName!=null'> and p.prod_name like #{param.prodName}</if>" +
            "<if test='param.prodNo!=null'> and p.prod_no like #{param.prodNo}</if>" +
            "<if test='param.prodTypeName!=null'> and pt.prod_type_name like #{param.prodTypeName}</if>" +
            "</script>")
    int findProductRowByParam(@Param("param")Map param);

    @Insert("insert into product(prod_name,prod_no,prod_type_id,prod_state) " +
            "values(#{prodName},#{prodNo},#{productType.prodTypeId},0)")
    void insertProduct(Product product);

    @Select("select * from product p left join product_type pt on p.prod_type_id=pt.prod_type_id where 1=1 and p.prod_state=0" +
            " and p.prod_type_id=#{prodTypeId}")
    @ResultMap("product")
    List<Product> findProductByProdType(Integer prodTypeId);

    @Update("update product set prod_state=1 where prod_id=#{productId}")
    void deleteProduct(int productId);

    @Select("select * from product p left join product_type pt on p.prod_type_id=pt.prod_type_id where 1=1 and p.prod_state=0" +
            " and prod_id=#{productId}")
    @ResultMap("product")
    Product findProductById(int productId);

    @Update("update product set prod_name=#{prodName},prod_no=#{prodNo}," +
            "prod_type_id=#{productType.prodTypeId} where prod_id=#{prodId}")
    void updateProduct(Product product);

    @Select("select * from product p left join product_type pt on p.prod_type_id=pt.prod_type_id " +
            "where p.prod_state=0 and prod_id not in(select prod_id from product_stock)")
    @ResultMap("product")
    List<Product> productListForStock();
}
