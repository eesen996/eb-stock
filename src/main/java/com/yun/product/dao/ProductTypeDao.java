package com.yun.product.dao;

import com.yun.product.model.ProductType;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductTypeDao {

    @Results(id="productType",value = {
            @Result(column = "prod_type_id",property = "prodTypeId"),
            @Result(column = "prod_type_name",property = "prodTypeName"),
            @Result(column = "parent_type_id",property = "parentProdType.prodTypeId"),
            @Result(column = "parent_type_name",property = "parentProdType.prodTypeName"),
            @Result(property = "childNodeList",
                    many = @Many(select = "com.yun.product.dao.ProductTypeDao.findProductTypeRoot"),
                    column = "prod_type_id"
            )
    })
    @Select("select p1.prod_type_id,p1.prod_type_name,p1.parent_id,p2.prod_type_id parent_type_id,p2.prod_type_name parent_type_name" +
            " from product_type p1 LEFT JOIN product_type p2 on p1.parent_id=p2.prod_type_id where p1.parent_id=#{parentId}")
    List<ProductType> findProductTypeRoot(int parentId);

    @Select("select p1.prod_type_id,p1.prod_type_name,p1.parent_id,p2.prod_type_id parent_type_id,p2.prod_type_name parent_type_name" +
            " from product_type p1 LEFT JOIN product_type p2 on p1.parent_id=p2.prod_type_id where p1.prod_type_id=#{prodTypeId}")
    @ResultMap("productType")
    ProductType findProductTypeById(int prodTypeId);

    @Insert("insert into product_type(prod_type_name,parent_id) values(#{prodTypeName},#{parentProdType.prodTypeId})")
    void insertProductType(ProductType productType);

    @Update("update product_type set prod_type_name=#{prodTypeName},parent_id=#{parentProdType.prodTypeId}" +
            " where prod_type_id=#{prodTypeId}")
    void updateProductType(ProductType productType);

    @Delete("delete from product_type where prod_type_id=#{prodTypeId}")
    void deleteProductType(int prodTypeId);
}
