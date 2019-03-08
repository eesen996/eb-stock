package com.yun.product.dao;

import com.yun.product.model.ProductParameter;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductParameterDao {

    @Results(id="prod_param",value = {
            @Result(property = "prodParamId",column = "prod_param_id"),
            @Result(property = "prodParamName",column = "prod_param_name"),
            @Result(property = "prodId",column = "prod_id")
    })
    @Select("select * from product_parameter where param_state=0 and prod_id=#{productId}")
    List<ProductParameter> findProductParameterListByProductId(int productId);

    @Insert("insert into product_parameter(prod_param_name,prod_id) values(#{prodParamName},#{prodId})")
    void insertProductParameter(ProductParameter productParameter);

    //@Delete("delete from product_parameter where prod_param_id=#{prodParamId}")
    @Update("update product_parameter set param_state=1 where prod_param_id=#{prodParamId}")
    void deleteProductParameter(int prodParamId);
}
