package com.yun.stock.model;

import com.yun.product.model.Product;
import com.yun.product.model.ProductParameter;
import lombok.Data;

@Data
public class ProductStock {

    private Integer prodStockId;
    private Product product;
    private ProductParameter productParameter;
    private Integer stockNum;
}
